(function() {
	var t, n, o, i, c, e, r;
	n = $(window), t = $(document), e = {
		wrapper: $(".grid-wrapper"),
		item: $(".grid-item")
	}, e.wrapper.hide(), c = $(".control-button"), i = {
		one: $(".control-button.one"),
		two: $(".control-button.two"),
		three: $(".control-button.three"),
		four: $(".control-button.four"),
		five: $(".control-button.five"),
		six: $(".control-button.six"),
		seven: $(".control-button.seven"),
		eight: $(".control-button.eight"),
		nine: $(".control-button.nine"),
		ten: $(".control-button.ten"),
		eleven: $(".control-button.eleven"),
		twelve: $(".control-button.twelve"),
		bottom_one: $(".control-button.bottom-one")
	}, r = {
		one: $(".control-input.one")
	}, o = function(t) {
		var n, o;
		n = $('<div class="grid-item"><img src="' + t + '"></div>'), o = e.wrapper, $.imgload(t, function() {
			return n.appendTo(e.wrapper), $.stackgrid.append(n)
		})
	}, n.on("load", function() {
		return e.wrapper.show().fadeIn(), $.stackgrid(".grid-wrapper", ".grid-item", {
			move: function(t, n, o, i) {
				t.stop().velocity({
					left: n,
					top: o
				}, 500, function() {
					return i()
				})
			},
			scale: function(t, n, o, i) {
				t.stop().velocity({
					height: n,
					width: o
				}, function() {
					return i()
				})
			}
		})
		//			$.stackgrid.config.column_width = 200, $.stackgrid.reset(), $.stackgrid.restack()
	})
}).call(this);