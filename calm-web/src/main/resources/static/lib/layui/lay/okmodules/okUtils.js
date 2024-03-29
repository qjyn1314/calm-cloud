"use strict";
layui.define(["layer", "okCookie", "table", "dtree", "layer"], function (exprots) {
    /**
     * 服务器地址-gateway地址 nginx代理 http://127.0.0.1:10082;
     */
    const baseUrl = "http://gateway-dev.calm.com";
    var $ = layui.jquery;
    var table = layui.table;
    let dtree = layui.dtree;
    let layer = layui.layer;
    var okUtils = {
        /**
         * 是否前后端分离
         */
        isFrontendBackendSeparate: true,
        /**
         * 获取用户token的key
         */
        tokenKey: "Authorization",
        /**
         * cookie过期时间
         */
        cookieExpires: 1,
        /**
         * cookie跨域所支持的域名
         */
        cookieDomain: 'calm.com',
        /**
         * cookie所属的路径
         */
        cookiePath: '/',
        //登录接口
        login: baseUrl + "/auth/login",
        //退出登录接口
        logout: baseUrl + "/auth/logout",
        //获取当前登录用户信息
        userInfo: baseUrl + "/web/currentUser",
        //查询菜单列表
        menuPage: baseUrl + "/user/api/v1/sysMenu/page",
        //查询菜单树
        menuTree: baseUrl + "/user/api/v1/sysMenu/formSelectTree",
        //保存菜单
        menuSave: baseUrl + "/user/api/v1/sysMenu/save",
        //更新菜单
        menuUpdate: baseUrl + "/user/api/v1/sysMenu/update",
        //更新菜单
        menuSelectMenuByCode: baseUrl + "/user/api/v1/sysMenu/selectMenuByCode",
        //查询角色列表
        rolePage: baseUrl + "/user/api/v1/sysRole/page",
        //保存角色
        roleSave: baseUrl + "/user/api/v1/sysRole/save",
        //更新角色
        roleUpdate: baseUrl + "/user/api/v1/sysRole/update",
        //角色分配菜单
        roleDistributionMenu: baseUrl + "/user/api/v1/sysRole/distributionMenu",
        //用户列表
        userPage: baseUrl + "/user/api/v1/sysUser/page",
        //保存用户
        userSave: baseUrl + "/user/api/v1/sysUser/save",
        //更新用户
        userUpdate: baseUrl + "/user/api/v1/sysUser/update",
        //审核通过
        userAudit: baseUrl + "/user/api/v1/sysUser/audit",
        //用户停用
        userDisable: baseUrl + "/user/api/v1/sysUser/disable",
        //角色树
        roleTree: baseUrl + "/user/api/v1/sysRole/roleTree",
        //用户分配角色
        userDistributionRole: baseUrl + "/user/api/v1/sysUser/userDistributionRole",
        //用户修改密码
        changeUserPwd: baseUrl + "/user/api/v1/sysUser/changePwd",
        setHeader: function setHeader() {
            return {
                //参考：https://www.cnblogs.com/cdwp8/p/5157377.html
                // https://blog.csdn.net/xutongbao/article/details/82834963
                // https://www.jb51.net/article/182545.htm
                //设置请求头信息
                "Authorization": $.cookie(okUtils.tokenKey),
            }
        },
        /**
         * 封装默认表格
         *
         * @param params
         * @returns {*}
         */
        tableInit: function (params) {
            const defaultSetting = {
                page: true,
                skin: 'nob',
                limit: 10,
                limits: [10, 20, 50, 100],
                autoSort: false,
                defaultToolbar: ["filter"],
                size: "sm",
                headers: okUtils.setHeader(),
                request: {
                    pageName: 'current',
                    limitName: 'pageSize'
                },
                response: {
                    statusCode: 0
                },
                parseData: function (res) {
                    return {
                        "code": res.code,
                        "count": res.data.total,
                        "data": res.data.records
                    };
                }
            };
            return table.render($.extend(defaultSetting, params));
        },
        /**
         * 初始化在下拉选择框
         * @param params
         */
        dtreeInitSelect: function (params) {
            let defaultParams = {
                select: true //下拉选择框中展示dtree树
            };
            defaultParams = $.extend(okUtils.dtreeInitParams(), defaultParams);
            dtree.render($.extend(defaultParams, params))
        },
        /**
         * 初始化在div盒子中的树
         * @param params
         */
        dtreeInitDiv: function (params) {
            let defaultParams = {
                toolbar: true //开启在盒子中展示dtree树
            };
            defaultParams = $.extend(okUtils.dtreeInitParams(), defaultParams);
            dtree.render($.extend(defaultParams, params))
        },
        /**
         * dtree树的默认样式参数
         */
        dtreeInitParams: function () {
            return {
                width: "100%",
                method: 'get',
                headers: okUtils.setHeader(),
                type: "all",
                line: true,
                ficon: ["1", "-1"],
                icon: "-1",
                initLevel: 5,
            }
        },
        /**
         * ajax()函数二次封装-传参为json，即：contentType: "application/json;charset=utf-8"
         * @param url
         * @param type
         * @param params
         * @param load
         * @returns {*|never|{always, promise, state, then}}
         */
        ajax: function (url, type, params, load) {
            var deferred = $.Deferred();
            var loadIndex;
            //参考：https://www.runoob.com/jquery/ajax-ajax.html
            $.ajax({
                url: url,
                type: type || "get",
                data: params || {},
                dataType: "json",
                //参考：https://www.runoob.com/http/http-content-type.html
                contentType: "application/json;charset=utf-8",
                headers: okUtils.setHeader(),
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    if (data.code === 0) {
                        deferred.resolve(data)
                    } else {
                        deferred.reject(data);
                    }
                },
                error: function () {
                    layer.close(loadIndex);
                    layer.msg("服务器错误", {icon: 2, time: 2000});
                    deferred.reject("服务器异常。");
                },
                complete: function () {
                    if (load) {
                        layer.close(loadIndex);
                    }
                },
            });
            return deferred.promise();
        },
        /**
         * ajax()函数二次封装-传参为form表单提交，即：contentType: "application/x-www-form-urlencoded;charset=utf-8"
         * @param url
         * @param type
         * @param params
         * @param load
         * @returns {*|never|{always, promise, state, then}}
         */
        ajaxForm: function (url, type, params, load) {
            var deferred = $.Deferred();
            var loadIndex;
            //参考：https://www.runoob.com/jquery/ajax-ajax.html
            $.ajax({
                url: url,
                type: type || "get",
                data: params || {},
                dataType: "json",
                //参考：https://www.runoob.com/http/http-content-type.html
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                headers: okUtils.setHeader(),
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    if (data.code === 0) {
                        deferred.resolve(data)
                    } else {
                        deferred.reject(data);
                    }
                },
                error: function () {
                    layer.close(loadIndex);
                    layer.msg("服务器错误", {icon: 2, time: 2000});
                    deferred.reject("服务器异常。");
                },
                complete: function () {
                    if (load) {
                        layer.close(loadIndex);
                    }
                },
            });
            return deferred.promise();
        },
        /**
         * 获取body的总宽度
         */
        getBodyWidth: function () {
            return document.body.scrollWidth;
        },
        /**
         * 主要用于针对表格批量操作操作之前的检查
         * @param table
         * @returns {string}
         */
        tableBatchCheck: function (table) {
            var checkStatus = table.checkStatus("tableId");
            var rows = checkStatus.data.length;
            if (rows > 0) {
                var idsStr = "";
                for (var i = 0; i < checkStatus.data.length; i++) {
                    idsStr += checkStatus.data[i].id + ",";
                }
                return idsStr;
            } else {
                layer.msg("未选择有效数据", {offset: "t", anim: 6});
            }
        },
        /**
         * 在表格页面操作成功后弹窗提示
         * @param content
         */
        tableSuccessMsg: function (content) {
            layer.msg(content, {icon: 1, time: 1000}, function () {
                // 刷新当前页table数据
                $(".layui-laypage-btn")[0].click();
            });
        },
        /**
         * sessionStorage 二次封装
         */
        session: function (name, value) {
            if (value) {
                /**设置*/
                if (typeof value == "object") {
                    sessionStorage.setItem(name, JSON.stringify(value));
                } else {
                    sessionStorage.setItem(name, value);
                }
            } else if (null !== value) {
                /**获取*/
                let val = sessionStorage.getItem(name);
                try {
                    val = JSON.parse(val);
                    return val;
                } catch (err) {
                    return val;
                }
            } else {
                /**清除*/
                return sessionStorage.removeItem(name);
            }
        },
        /**
         * localStorage 二次封装
         */
        local: function (name, value) {
            if (value) {
                /**设置*/
                if (typeof value == "object") {
                    localStorage.setItem(name, JSON.stringify(value));
                } else {
                    localStorage.setItem(name, value);
                }
            } else if (null !== name) {
                /**获取*/
                let val = localStorage.getItem(name);
                try {
                    val = JSON.parse(val);
                    return val;
                } catch (err) {
                    return val;
                }
            } else {
                /**清除*/
                return localStorage.removeItem(name);
            }
        },
        /**
         * 获取父窗体的okTab
         * @returns {string}
         */
        getOkTab: function () {
            return parent.objOkTab;
        },
        /**
         * 格式化当前日期
         * @param date
         * @param fmt
         * @returns {void | string}
         */
        dateFormat: function (date, fmt) {
            date = date || new Date();
            fmt = fmt || "yyyy年M月s日";
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        },
        number: {
            /**
             * 判断是否为一个正常的数字
             * @param num
             */
            isNumber: function (num) {
                return !(num && !isNaN(num));
            },
            /**
             * 判断一个数字是否包括在某个范围
             * @param num
             * @param begin
             * @param end
             */
            isNumberWith: function (num, begin, end) {
                if (this.isNumber(num)) {
                    return num >= begin && num <= end;
                }
            },
        },

    };
    exprots("okUtils", okUtils);
});

var date_format = function (timestamp, format) {
    format = format || 'yyyy年MM月dd';
    timestamp = timestamp + "";
    if (timestamp * 1 > 0 && timestamp.length == 10) {
        timestamp = timestamp * 1000;
    }
    // 通过getDate()方法获取date类型的时间
    var regYear = new RegExp("(y+)", "i");
    var realDate = new Date(timestamp);

    function timeFormat(num) {
        return num < 10 ? '0' + num : num;
    }

    var date = [
        ["M+", timeFormat(realDate.getMonth() + 1)],
        ["d+", timeFormat(realDate.getDate())],
        ["h+", timeFormat(realDate.getHours())],
        ["m+", timeFormat(realDate.getMinutes())],
        ["s+", timeFormat(realDate.getSeconds())],
        ["q+", Math.floor((realDate.getMonth() + 3) / 3)],
        ["S+", realDate.getMilliseconds()],
    ];
    var reg1 = regYear.exec(format);
    if (reg1) {
        format = format.replace(reg1[1], (realDate.getFullYear() + '').substring(4 - reg1[1].length));
    }
    for (var i = 0; i < date.length; i++) {
        var k = date[i][0];
        var v = date[i][1];
        // getRegExp初始化一个正则表达式对象
        var reg2 = new RegExp("(" + k + ")").exec(format);
        if (reg2) {
            format = format.replace(reg2[1], reg2[1].length == 1
                ? v : ("00" + v).substring(("" + v).length));
        }
    }
    return format;
};