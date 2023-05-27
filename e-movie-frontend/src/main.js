import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
import store from './store';
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// import axios from 'axios'

import { clickOutside } from './directives/clickOutside';

const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.use(store)

app.directive('click-outside', clickOutside);
app.mount('#app');
