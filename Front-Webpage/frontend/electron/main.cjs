const { app, BrowserWindow, ipcMain } = require('electron')
const fs = require('fs')
const path = require('path')

const isDev = !app.isPackaged
const isWindows = process.platform === 'win32'
const APP_USER_MODEL_ID = 'io.xenosoft.imld.frontend'
const SHELL_CHANNELS = {
  minimize: 'shell:minimize',
  toggleMaximize: 'shell:toggle-maximize',
  close: 'shell:close',
  getWindowState: 'shell:get-window-state',
  stateChanged: 'shell:state-changed'
}

function getWindowState(win) {
  return {
    isMaximized: win.isMaximized(),
    isFullscreen: win.isFullScreen()
  }
}

function emitWindowState(win) {
  if (!win.isDestroyed()) {
    win.webContents.send(SHELL_CHANNELS.stateChanged, getWindowState(win))
  }
}

function withWindow(event, callback) {
  const win = BrowserWindow.fromWebContents(event.sender)
  if (!win || win.isDestroyed()) {
    return undefined
  }

  return callback(win)
}

function registerWindowEvents(win) {
  win.on('maximize', () => emitWindowState(win))
  win.on('unmaximize', () => emitWindowState(win))
  win.on('enter-full-screen', () => emitWindowState(win))
  win.on('leave-full-screen', () => emitWindowState(win))
}

function registerShellIpc() {
  ipcMain.on(SHELL_CHANNELS.minimize, (event) => {
    withWindow(event, (win) => win.minimize())
  })

  ipcMain.on(SHELL_CHANNELS.toggleMaximize, (event) => {
    withWindow(event, (win) => {
      if (win.isMaximized()) {
        win.unmaximize()
        return
      }

      win.maximize()
    })
  })

  ipcMain.on(SHELL_CHANNELS.close, (event) => {
    withWindow(event, (win) => win.close())
  })

  ipcMain.handle(SHELL_CHANNELS.getWindowState, (event) =>
    withWindow(event, (win) => getWindowState(win))
  )
}

function resolveWindowIconPath() {
  const candidatePaths = app.isPackaged
    ? [
        path.join(process.resourcesPath, 'icon.png'),
        path.join(process.resourcesPath, 'icon.ico')
      ]
    : [
        path.join(__dirname, '../src/assets/imld-icon.png'),
        path.join(__dirname, '../build/icon.ico')
      ]

  for (const candidatePath of candidatePaths) {
    if (fs.existsSync(candidatePath)) {
      return candidatePath
    }
  }

  return undefined
}

function createMainWindow() {
  const iconPath = resolveWindowIconPath()
  const win = new BrowserWindow({
    width: 1366,
    height: 860,
    minWidth: 1024,
    minHeight: 720,
    title: '遗传代谢性肝病（IMLD）早期筛查与辅助诊断系统',
    icon: iconPath,
    frame: !isWindows,
    backgroundColor: '#eef3f8',
    autoHideMenuBar: true,
    webPreferences: {
      preload: path.join(__dirname, 'preload.cjs'),
      contextIsolation: true,
      nodeIntegration: false,
      sandbox: true
    }
  })

  win.setMenuBarVisibility(false)
  registerWindowEvents(win)

  if (isDev) {
    const rendererUrl = process.env.ELECTRON_RENDERER_URL || 'http://127.0.0.1:5173'
    win.loadURL(rendererUrl)
    win.webContents.openDevTools({ mode: 'detach' })
    return
  }

  win.loadFile(path.join(__dirname, '../dist/index.html'))
}

app.whenReady().then(() => {
  if (isWindows) {
    app.setAppUserModelId(APP_USER_MODEL_ID)
  }
  registerShellIpc()
  createMainWindow()

  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) {
      createMainWindow()
    }
  })
})

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('browser-window-created', (_event, window) => {
  if (isWindows) {
    window.webContents.once('did-finish-load', () => {
      window.webContents.send(SHELL_CHANNELS.stateChanged, getWindowState(window))
    })
  }
})
