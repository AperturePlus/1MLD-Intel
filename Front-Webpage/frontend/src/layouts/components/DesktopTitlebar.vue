<template>
  <header class="desktop-titlebar" :class="{ 'is-maximized': windowState.isMaximized }">
    <div class="titlebar-brand drag-region">
      <span class="brand-mark">
        <span class="brand-mark__dot"></span>
      </span>
      <div class="brand-copy">
        <span class="brand-copy__eyebrow">IMLD Intelligence</span>
        <strong class="brand-copy__name">智能医生工作站</strong>
      </div>
    </div>

    <div class="titlebar-context drag-region" @dblclick="toggleMaximize">
      <span class="context-section">{{ currentSectionTitle }}</span>
      <strong class="context-title">{{ currentPageTitle }}</strong>
    </div>

    <div class="titlebar-actions no-drag">
      <button
        v-if="showHomeShortcut"
        type="button"
        class="home-shortcut"
        :class="{ 'is-active': route.path === homePath }"
        @click="goHome"
      >
        <span class="home-shortcut__icon"></span>
        <span>首页</span>
      </button>

      <div class="window-controls">
        <button
          type="button"
          class="window-control"
          aria-label="最小化"
          @click="minimizeWindow"
        >
          <span class="window-control__icon window-control__icon--minimize"></span>
        </button>
        <button
          type="button"
          class="window-control"
          :aria-label="windowState.isMaximized ? '还原窗口' : '最大化窗口'"
          @click="toggleMaximize"
        >
          <span
            class="window-control__icon"
            :class="
              windowState.isMaximized
                ? 'window-control__icon--restore'
                : 'window-control__icon--maximize'
            "
          ></span>
        </button>
        <button
          type="button"
          class="window-control window-control--close"
          aria-label="关闭窗口"
          @click="closeWindow"
        >
          <span class="window-control__icon window-control__icon--close"></span>
        </button>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const shell = window.electron?.shell
const homePath = '/center/welcome'
const windowState = ref<ElectronWindowState>({
  isMaximized: false,
  isFullscreen: false
})

const currentSectionTitle = computed(() => route.meta.sectionTitle ?? '临床工作台')
const currentPageTitle = computed(() => route.meta.title ?? '首页')
const showHomeShortcut = computed(() => route.path.startsWith('/center'))

const syncWindowState = async () => {
  const nextState = await shell?.getWindowState()
  if (nextState) {
    windowState.value = nextState
  }
}

const goHome = () => {
  if (route.path !== homePath) {
    router.push(homePath)
  }
}

const minimizeWindow = () => {
  shell?.minimize()
}

const toggleMaximize = () => {
  shell?.toggleMaximize()
}

const closeWindow = () => {
  shell?.close()
}

let removeWindowStateListener: (() => void) | undefined

onMounted(async () => {
  await syncWindowState()
  removeWindowStateListener = shell?.onWindowStateChange((nextState) => {
    windowState.value = nextState
  })
})

onBeforeUnmount(() => {
  removeWindowStateListener?.()
  document.documentElement.classList.remove('electron-shell-maximized')
})

watch(
  () => windowState.value.isMaximized,
  (isMaximized) => {
    document.documentElement.classList.toggle('electron-shell-maximized', isMaximized)
  },
  { immediate: true }
)
</script>

<style scoped>
.desktop-titlebar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 2000;
  display: grid;
  grid-template-columns: minmax(220px, auto) minmax(0, 1fr) auto;
  align-items: center;
  gap: 16px;
  height: var(--electron-titlebar-safe-top);
  padding: 0 10px 0 14px;
  background: var(--electron-titlebar-bg);
  border-bottom: 1px solid var(--electron-titlebar-border);
  box-shadow: var(--electron-titlebar-shadow);
  backdrop-filter: blur(18px);
  user-select: none;
}

.desktop-titlebar.is-maximized {
  box-shadow: none;
}

.drag-region {
  -webkit-app-region: drag;
}

.no-drag {
  -webkit-app-region: no-drag;
}

.titlebar-brand {
  min-width: 0;
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-mark {
  width: 30px;
  height: 30px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  background: linear-gradient(160deg, rgba(15, 109, 141, 0.16), rgba(34, 163, 159, 0.28));
  border: 1px solid rgba(15, 109, 141, 0.16);
}

.brand-mark__dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background: linear-gradient(155deg, #0f6d8d, #22a39f);
  box-shadow: 0 0 0 4px rgba(34, 163, 159, 0.12);
}

.brand-copy {
  min-width: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.brand-copy__eyebrow {
  font-size: 11px;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  color: #6c7a89;
}

.brand-copy__name {
  font-size: 13px;
  color: #203143;
  white-space: nowrap;
}

.titlebar-context {
  min-width: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 2px;
}

.context-section {
  font-size: 11px;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: #718295;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.context-title {
  font-size: 14px;
  color: #1f2f41;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.titlebar-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.home-shortcut {
  height: 32px;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 0 12px;
  border: 1px solid rgba(148, 163, 184, 0.32);
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.78);
  color: #2f4257;
  cursor: pointer;
  transition:
    background-color 0.2s ease,
    border-color 0.2s ease,
    transform 0.2s ease;
}

.home-shortcut:hover {
  background: #ffffff;
  border-color: rgba(34, 163, 159, 0.32);
  transform: translateY(-1px);
}

.home-shortcut.is-active {
  background: rgba(15, 109, 141, 0.1);
  border-color: rgba(15, 109, 141, 0.22);
}

.home-shortcut__icon {
  width: 10px;
  height: 10px;
  display: inline-block;
  border: 1.4px solid currentColor;
  border-bottom-width: 0;
  transform: translateY(1px) rotate(45deg);
  border-radius: 2px 2px 0 0;
}

.window-controls {
  display: inline-flex;
  align-items: stretch;
  margin-left: 2px;
  border-radius: 14px;
  overflow: hidden;
  border: 1px solid rgba(148, 163, 184, 0.26);
  background: rgba(255, 255, 255, 0.56);
}

.window-control {
  position: relative;
  width: 46px;
  height: calc(var(--electron-titlebar-safe-top) - 10px);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  border: 0;
  background: transparent;
  color: #32475b;
  cursor: pointer;
  transition:
    background-color 0.16s ease,
    color 0.16s ease;
}

.window-control:hover {
  background: rgba(15, 23, 42, 0.08);
}

.window-control--close:hover {
  background: #e5484d;
  color: #ffffff;
}

.window-control__icon {
  position: relative;
  width: 10px;
  height: 10px;
  display: inline-block;
}

.window-control__icon--minimize::before {
  content: '';
  position: absolute;
  left: 0;
  right: 0;
  top: 50%;
  height: 1.4px;
  background: currentColor;
  transform: translateY(-50%);
}

.window-control__icon--maximize {
  border: 1.4px solid currentColor;
}

.window-control__icon--restore::before,
.window-control__icon--restore::after {
  content: '';
  position: absolute;
  width: 8px;
  height: 8px;
  border: 1.4px solid currentColor;
  background: transparent;
}

.window-control__icon--restore::before {
  top: 1px;
  right: 0;
}

.window-control__icon--restore::after {
  left: 0;
  bottom: 0;
  background: var(--electron-titlebar-bg);
}

.window-control__icon--close::before,
.window-control__icon--close::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 12px;
  height: 1.4px;
  background: currentColor;
}

.window-control__icon--close::before {
  transform: translate(-50%, -50%) rotate(45deg);
}

.window-control__icon--close::after {
  transform: translate(-50%, -50%) rotate(-45deg);
}

@media (max-width: 960px) {
  .desktop-titlebar {
    grid-template-columns: auto minmax(0, 1fr) auto;
    gap: 12px;
  }

  .brand-copy__eyebrow {
    display: none;
  }

  .context-section {
    display: none;
  }
}

@media (max-width: 720px) {
  .brand-copy__name,
  .home-shortcut {
    display: none;
  }

  .window-control {
    width: 42px;
  }
}
</style>
