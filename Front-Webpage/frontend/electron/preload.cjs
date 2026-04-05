const { contextBridge, ipcRenderer } = require('electron')

const isWindows = process.platform === 'win32'
const TITLE_BAR_HEIGHT = 48
const SHELL_CHANNELS = {
  minimize: 'shell:minimize',
  toggleMaximize: 'shell:toggle-maximize',
  close: 'shell:close',
  getWindowState: 'shell:get-window-state',
  stateChanged: 'shell:state-changed'
}

contextBridge.exposeInMainWorld('electron', {
  platform: process.platform,
  versions: process.versions,
  shell: {
    platform: process.platform,
    frameless: isWindows,
    height: isWindows ? TITLE_BAR_HEIGHT : 0,
    getWindowState: () => ipcRenderer.invoke(SHELL_CHANNELS.getWindowState),
    minimize: () => ipcRenderer.send(SHELL_CHANNELS.minimize),
    toggleMaximize: () => ipcRenderer.send(SHELL_CHANNELS.toggleMaximize),
    close: () => ipcRenderer.send(SHELL_CHANNELS.close),
    onWindowStateChange: (callback) => {
      const listener = (_event, nextState) => callback(nextState)
      ipcRenderer.on(SHELL_CHANNELS.stateChanged, listener)
      return () => {
        ipcRenderer.removeListener(SHELL_CHANNELS.stateChanged, listener)
      }
    }
  }
})
