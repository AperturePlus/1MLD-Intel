<template>
  <div ref="container" class="liver-3d-container"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import * as THREE from 'three';

const container = ref<HTMLElement | null>(null);
let animationFrameId = 0;
let scene: THREE.Scene, camera: THREE.PerspectiveCamera, renderer: THREE.WebGLRenderer;
let liverMesh: THREE.Points;
let group: THREE.Group;

const initThreeJS = () => {
  if (!container.value) return;

  const width = container.value.clientWidth;
  const height = container.value.clientHeight;

  scene = new THREE.Scene();

  camera = new THREE.PerspectiveCamera(45, width / height, 0.1, 1000);
  camera.position.z = 25;

  renderer = new THREE.WebGLRenderer({ alpha: true, antialias: true });
  renderer.setSize(width, height);
  renderer.setPixelRatio(window.devicePixelRatio);
  container.value.appendChild(renderer.domElement);

  group = new THREE.Group();
  scene.add(group);

  // Create a stylized liver shape
  createLiverParticles();

  // Handle Resize
  window.addEventListener('resize', onWindowResize);

  animate();
};

const createLiverParticles = () => {
  // Use a highly tessellated sphere as a base
  const geometry = new THREE.SphereGeometry(6, 128, 128);
  const positions = geometry.attributes.position;
  
  // Create an array to hold colors
  const colors = [];
  const color1 = new THREE.Color('#34D3B8'); // Cyan/Teal
  const color2 = new THREE.Color('#0F6D8D'); // Dark Blue
  const color3 = new THREE.Color('#F8FCFF'); // White

  for (let i = 0; i < positions.count; i++) {
    let x = positions.getX(i);
    let y = positions.getY(i);
    let z = positions.getZ(i);

    const nx = x / 6;

    let sx = 1.0;
    let sy = 1.0;
    let sz = 1.0;

    // Deform the sphere into a stylized liver
    if (nx > 0) {
      // Right Lobe - thick, rounded
      sx = 1.0 + nx * 0.2;
      sy = 1.0 + nx * 0.15;
      sz = 1.0 + nx * 0.25;
    } else {
      // Left Lobe - tapering smoothly without being sharp
      sx = 1.0 + Math.abs(nx) * 0.9;
      sy = 1.0 - Math.abs(nx) * 0.35;
      sz = 1.0 - Math.abs(nx) * 0.35;
    }

    // Adjust y scaling to keep the top edge relatively horizontal
    if (y > 0) {
      y *= (1.0 + (sy - 1.0) * 0.3); // Top part tapers much less
    } else {
      y *= sy; // Bottom part tapers fully
    }

    x *= sx;
    z *= sz;

    // Removed the asymmetrical parabolic curvature that caused the liver to look slanted/tilted

    // Add some noise to make it look organic/particly
    const noiseAmp = 0.15;
    x += (Math.random() - 0.5) * noiseAmp;
    y += (Math.random() - 0.5) * noiseAmp;
    z += (Math.random() - 0.5) * noiseAmp;

    positions.setXYZ(i, x, y, z);
    
    // Mix colors based on position
    const mixedColor = color1.clone().lerp(color2, Math.abs(nx));
    if (Math.random() > 0.95) mixedColor.lerp(color3, 0.8);
    colors.push(mixedColor.r, mixedColor.g, mixedColor.b);
  }

  geometry.computeVertexNormals();

  // Filter out points to make it a point cloud rather than a solid dense surface
  //const validParticles = positions.count * 0.3; // 30% of particles
  const filteredPositions = [];
  const filteredColors = [];
  for (let i = 0; i < positions.count; i++) {
    if (Math.random() < 0.3) {
      filteredPositions.push(positions.getX(i), positions.getY(i), positions.getZ(i));
      filteredColors.push(colors[i * 3], colors[i * 3 + 1], colors[i * 3 + 2]);
    }
  }

  const finalGeometry = new THREE.BufferGeometry();
  finalGeometry.setAttribute('position', new THREE.Float32BufferAttribute(filteredPositions, 3));
  finalGeometry.setAttribute('color', new THREE.Float32BufferAttribute(filteredColors, 3));

  const material = new THREE.PointsMaterial({
    size: 0.06,
    vertexColors: true,
    transparent: true,
    opacity: 0.8,
    blending: THREE.AdditiveBlending,
  });

  liverMesh = new THREE.Points(finalGeometry, material);
  
  // Center mesh
  finalGeometry.computeBoundingBox();
  const center = new THREE.Vector3();
  finalGeometry.boundingBox?.getCenter(center);
  liverMesh.position.sub(center);

  // Group to handle mouse rotations easily around center
  const liverWrapper = new THREE.Group();
  liverWrapper.add(liverMesh);

  // Add an inner glowing wireframe to give depth
  const wireMat = new THREE.LineBasicMaterial({
     color: 0x34D3B8,
     transparent: true,
     opacity: 0.03,
     blending: THREE.AdditiveBlending
  });
  const wireframe = new THREE.WireframeGeometry(geometry);
  const line = new THREE.LineSegments(wireframe, wireMat);
  line.position.copy(liverMesh.position);
  liverWrapper.add(line);

  group.add(liverWrapper);
};

const animate = () => {
  animationFrameId = requestAnimationFrame(animate);

  // Auto rotation only (no mouse interaction)
  group.rotation.y += 0.0025;
  group.rotation.x = 0; // maintain horizontal shape
  
  // Gentle floating
  group.position.y = Math.sin(Date.now() * 0.001) * 0.5;

  renderer.render(scene, camera);
};

const onWindowResize = () => {
  if (!container.value) return;
  const width = container.value.clientWidth;
  const height = container.value.clientHeight;
//   windowHalf.x = width / 2;
//   windowHalf.y = height / 2;

  camera.aspect = width / height;
  camera.updateProjectionMatrix();

  renderer.setSize(width, height);
};

onMounted(() => {
  setTimeout(() => initThreeJS(), 100);
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', onWindowResize);
  cancelAnimationFrame(animationFrameId);
  renderer.dispose();
});
</script>

<style scoped>
.liver-3d-container {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 1; /* Match floating particles z-index */
  pointer-events: none;
}
</style>