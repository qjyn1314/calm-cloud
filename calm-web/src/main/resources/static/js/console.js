"use strict";
layui.use(["okUtils", "table", "okCountUp", ], function () {
    var $ = layui.jquery;
    var countUp = layui.okCountUp;
    var okUtils = layui.okUtils;

    /**
     * 收入、商品、博客、用户
     */
    function statText() {
        var elem_nums = $(".stat-text");
        elem_nums.each(function (i, j) {
            var ran = parseInt(Math.random() * 99 + 1);
            !new countUp({
                target: j,
                endVal: 20 * ran
            }).start();
        });
    }

    statText();
});


