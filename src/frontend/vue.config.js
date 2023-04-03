module.exports = {
    transpileDependencies: [
        'vuetify'
    ],
    devServer: {
        port: 80,
        proxy: {
            '/api': {
                target: 'http://localhost:9000',
                ws: true,
                changeOrigin: true
            },
            '/connect': {
                target:`ws://localhost:9000`,
                ws: true,
                secure: false,
                changeOrigin: true
            }
        }
    }
}