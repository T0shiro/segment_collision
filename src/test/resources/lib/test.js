// Based on: https://nathandavison.com/article/17/using-qunit-and-requirejs-to-build-modular-unit-tests
requirejs.config({
    paths: {
        jquery: 'https://code.jquery.com/jquery-3.1.1',
        qunit: 'https://code.jquery.com/qunit/qunit-2.1.1',
        kotlin: 'kotlin',
        exampleApp: 'kotlin-js-example_main',
        exampleAppTest: 'kotlin-js-example_test'
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

requirejs(['qunit', 'kotlin-js-example_test'], function (qunit, exampleAppTest) {
    // run the tests.
    exampleAppTest.sample.myApp();

    // start QUnit.
    QUnit.load();
    QUnit.start();
});