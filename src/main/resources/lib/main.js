requirejs.config({

    paths: {
        jquery: 'jquery-3.1.1.min',
        qunit: 'qunit-2.1.1',
        kotlin: 'kotlin',
        exampleApp: 'kotlin-js-example_main'
    }
});

requirejs(["kotlin-js-example_main"], function (exampleApp) {
    exampleApp.sample.myApp();
});