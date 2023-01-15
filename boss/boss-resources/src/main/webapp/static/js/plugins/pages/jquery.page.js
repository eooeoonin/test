!function (a) {
  var b = {
    init: function (a, c) {
      return function () {
        b.fillHtml(a, c), b.bindEvent(a, c)
      }()
    }, fillHtml: function (a, b) {
      return function () {
        a.empty(), b.current > 1 ? a.append('<a href="javascript:;" class="prevPage">上一页</a>') : (a.remove(".prevPage"), a.append('<span class="disabled">上一页</span>')), 1 != b.current && b.current >= 4 && 4 != b.pageCount && a.append('<a href="javascript:;" class="tcdNumber">1</a>'), b.current - 2 > 2 && b.current <= b.pageCount && b.pageCount > 5 && a.append("<span>...</span>");
        var c = b.current - 2, d = b.current + 2;
        for ((c > 1 && b.current < 4 || 1 == b.current) && d++, b.current > b.pageCount - 4 && b.current >= b.pageCount && c--; d >= c; c++)c <= b.pageCount && c >= 1 && (c != b.current ? a.append('<a href="javascript:;" class="tcdNumber">' + c + "</a>") : a.append('<span class="current">' + c + "</span>"));
        b.current + 2 < b.pageCount - 1 && b.current >= 1 && b.pageCount > 5 && a.append("<span>...</span>"), b.current != b.pageCount && b.current < b.pageCount - 2 && 4 != b.pageCount && a.append('<a href="javascript:;" class="tcdNumber">' + b.pageCount + "</a>"), b.current < b.pageCount ? a.append('<a href="javascript:;" class="nextPage">下一页</a>') : (a.remove(".nextPage"), a.append('<span class="disabled">下一页</span>'))
      }()
    }, bindEvent: function (c, d) {
      return function () {
        c.unbind("click");
        c.on("click", "a.tcdNumber", function () {
          var e = parseInt(a(this).text());
          b.fillHtml(c, {current: e, pageCount: d.pageCount}), "function" == typeof d.backFn && d.backFn(e)
        }), c.on("click", "a.prevPage", function () {
          var a = parseInt(c.children("span.current").text());
          b.fillHtml(c, {current: a - 1, pageCount: d.pageCount}), "function" == typeof d.backFn && d.backFn(a - 1)
        }), c.on("click", "a.nextPage", function () {
          var a = parseInt(c.children("span.current").text());
          b.fillHtml(c, {current: a + 1, pageCount: d.pageCount}), "function" == typeof d.backFn && d.backFn(a + 1)
        })
      }()
    }
  };
  a.fn.createPage = function (c) {
    var d = a.extend({
      pageCount: 10, current: 1, backFn: function () {
      }
    }, c);
    b.init(this, d)
  }
}(jQuery);