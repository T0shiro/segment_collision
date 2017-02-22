requirejs.config({

    paths: {
        jquery: 'https://code.jquery.com/jquery-3.1.1',
        kotlin: 'kotlin',
        exampleApp: 'kotlin-js-example_main'
    }
});

requirejs(["kotlin-js-example_main"], function (exampleApp) {
    exampleApp.sample.myApp();
});