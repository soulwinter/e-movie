import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
// import axios from 'axios'

createApp(App).mount('#app')
createApp(App).use(router).mount('#app');
