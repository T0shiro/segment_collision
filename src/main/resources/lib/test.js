// Based on: https://nathandavison.com/article/17/using-qunit-and-requirejs-to-build-modular-unit-tests
requirejs.config({
    paths: {
        jquery: 'jquery-3.1.1.min',
        qunit: 'qunit-2.1.1',
        kotlin: 'kotlin',
        exampleApp: 'kotlin-js-example_main'
    }, shim: {
        'qunit': {
            exports: 'QUnit',
            init: function () {
                QUnit.config.autoload = false;
                QUnit.config.autostart = false;
            }
        }
    }
});

requirejs(['qunit', 'kotlin-js-example_main'], function (qunit, exampleApp) {
    // run the tests.
    exampleApp.test_sample.myApp();

    // start QUnit.
    QUnit.load();
    QUnit.start();
});