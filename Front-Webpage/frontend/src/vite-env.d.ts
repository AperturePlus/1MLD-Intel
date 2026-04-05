/// <reference types="vite/client" />

import 'vue-router'

declare global {
  interface ElectronWindowState {
    isMaximized: boolean
    isFullscreen: boolean
  }

  interface ElectronShellBridge {
    platform: string
    frameless: boolean
    height: number
    getWindowState: () => Promise<ElectronWindowState | undefined>
    minimize: () => void
    toggleMaximize: () => void
    close: () => void
    onWindowStateChange: (
      callback: (state: ElectronWindowState) => void
    ) => () => void
  }

  interface ElectronBridge {
    platform: string
    versions: Record<string, string>
    shell: ElectronShellBridge
  }

  interface Window {
    electron?: ElectronBridge
  }
}

declare module 'vue-router' {
  interface RouteMeta {
    title?: string
    sectionTitle?: string
  }
}

export {}
