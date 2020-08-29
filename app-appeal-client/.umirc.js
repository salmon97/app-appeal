// ref: https://umijs.org/config/
export default {
  treeShaking: true,
  routes: [
    {
      path: '/',
      component: '../layouts/index',
      routes: [
        { path: '/', component: '../pages/index' },
        { path: '/cabinet', component: '../pages/cabinet/index' }
      ]
    }
  ],
  plugins: [
    // ref: https://umijs.org/plugin/umi-plugin-react.html
    ['umi-plugin-react', {
      antd: false,
      dva: true,
      dynamicImport: {webpackChunkName: true},
      title: 'app-appeal-client',
      dll: false,
      routes: {
        exclude: [
          /models\//,
          /services\//,
          /model\.(t|j)sx?$/,
          /service\.(t|j)sx?$/,
          /components\//,
        ],
      },
    }],
  ],
  // proxy: {
  //   "/api": {
  //     "target": "http://localhost/",
  //     "changeOrigin": true
  //   }
  // },
  // outputPath: '../app-appeal-server/src/main/resources/static'
}
