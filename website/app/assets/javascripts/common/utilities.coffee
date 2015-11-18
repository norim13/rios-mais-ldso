@decode_utf8 = (s) ->
	return decodeURIComponent(escape(s));

@encode_utf8 = (s) ->
	return unescape(encodeURIComponent(s));
