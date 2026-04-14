<template>
  <canvas ref="canvasRef" class="floating-particles"></canvas>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue'

const canvasRef = ref<HTMLCanvasElement | null>(null)
let animationFrameId = 0

interface Particle {
  x: number
  y: number
  vx: number
  vy: number
  radius: number
  alpha: number
  color: string
}

const particles: Particle[] = []
let width = 0
let height = 0

const colors = ['#38BDF8', '#34D3B8', '#f8fcff']

const initParticles = () => {
  particles.length = 0
  // Adjust particle count based on screen width, with a max limit for performance
  const numParticles = Math.min(Math.floor(window.innerWidth / 16), 120)
  for (let i = 0; i < numParticles; i++) {
    particles.push({
      x: Math.random() * width,
      y: Math.random() * height,
      vx: (Math.random() - 0.5) * 0.6,
      vy: (Math.random() - 0.5) * 0.6,
      radius: Math.random() * 2 + 0.5,
      alpha: Math.random() * 0.5 + 0.2,
      color: colors[Math.floor(Math.random() * colors.length)]
    })
  }
}

const draw = (ctx: CanvasRenderingContext2D) => {
  ctx.clearRect(0, 0, width, height)
  
  // Update and draw particles
  particles.forEach(p => {
    p.x += p.vx
    p.y += p.vy
    
    // Wrap around screen edges
    if (p.x < 0) p.x = width
    if (p.x > width) p.x = 0
    if (p.y < 0) p.y = height
    if (p.y > height) p.y = 0
    
    // Draw particle
    ctx.beginPath()
    ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2)
    ctx.fillStyle = p.color
    ctx.globalAlpha = p.alpha
    ctx.fill()
  })

  // Draw connecting lines between close particles
  for (let i = 0; i < particles.length; i++) {
    for (let j = i + 1; j < particles.length; j++) {
      const dx = particles[i].x - particles[j].x
      const dy = particles[i].y - particles[j].y
      const dist = Math.sqrt(dx * dx + dy * dy)
      
      if (dist < 130) {
        ctx.beginPath()
        ctx.moveTo(particles[i].x, particles[i].y)
        ctx.lineTo(particles[j].x, particles[j].y)
        ctx.strokeStyle = particles[i].color
        ctx.lineWidth = 0.2
        ctx.globalAlpha = ((130 - dist) / 130) * 0.3
        ctx.stroke()
      }
    }
  }
}

const animate = () => {
  if (!canvasRef.value) return
  const ctx = canvasRef.value.getContext('2d')
  if (ctx) {
    draw(ctx)
  }
  animationFrameId = requestAnimationFrame(animate)
}

const handleResize = () => {
  if (!canvasRef.value) return
  // Use innerWidth/innerHeight for full screen coverage
  width = window.innerWidth
  height = window.innerHeight
  // Fix DPI scaling for sharper canvas on high resolution displays
  const dpr = window.devicePixelRatio || 1;
  canvasRef.value.width = width * dpr;
  canvasRef.value.height = height * dpr;
  const ctx = canvasRef.value.getContext('2d');
  if (ctx) {
    ctx.scale(dpr, dpr);
  }
  
  canvasRef.value.style.width = width + 'px';
  canvasRef.value.style.height = height + 'px';
  initParticles()
}

onMounted(() => {
  // Add a slight delay to ensure the container is fully rendered
  setTimeout(() => {
    handleResize()
    window.addEventListener('resize', handleResize)
    animate()
  }, 50)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  cancelAnimationFrame(animationFrameId)
})
</script>

<style scoped>
.floating-particles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 1; /* ensures it's above the background but below form content */
}
</style>