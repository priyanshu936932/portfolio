import { onMounted, onUnmounted } from 'vue'
import axios from 'axios'

const INTERVAL_MS = 90_000 // 90 seconds
const HEALTH_URL  = import.meta.env.VITE_API_BASE_URL
  ? import.meta.env.VITE_API_BASE_URL.replace(/\/api$/, '/actuator/health')
  : null

export function useKeepAlive() {
  let timer = null

  async function ping() {
    if (!HEALTH_URL) return
    try {
      await axios.get(HEALTH_URL, { timeout: 10_000 })
    } catch { /* silent — backend may be waking up */ }
  }

  onMounted(() => {
    ping()                              // ping immediately on load
    timer = setInterval(ping, INTERVAL_MS)
  })

  onUnmounted(() => clearInterval(timer))
}
