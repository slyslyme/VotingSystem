const path = require('path')
function resolve(dir) {
  return path.join(__dirname, dir)

}
module.exports ={
  chainWebpack:config => {
    config.resolve.alias
      .set('@', resolve('src'))
      .set('@pages', resolve('src/views/pages'))
  },
  publicPath: process.env.VUE_APP_BASE_PATH || '/'
}
