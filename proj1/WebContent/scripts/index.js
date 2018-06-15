var cache = {};

function handleLookup(query, doneCallback) {
	console.log("autocomplete initiated")
	
	if (query in cache) {
		console.log("Pulling from cache")
		console.log(cache[query]);
		doneCallback({suggestions: cache[query]});
		return;
	}
	console.log("sending AJAX request to backend Java Servlet")
	jQuery.ajax({
		"method": "GET", 
		"url": "Search?query=" + escape(query),
		"success": function(data) {
			handleLookupAjaxSuccess(data, query, doneCallback) 
		},
		"error": function(errorData) {
			console.log("lookup ajax error")
			console.log(errorData)
		}
	})
}

function handleLookupAjaxSuccess(data, query, doneCallback) {	
	var jsonData = JSON.parse(data);
	console.log(jsonData)
	
	cache[query] = jsonData;
	
	doneCallback( { suggestions: jsonData } );
}


function handleSelectSuggestion(suggestion) {
	
	query_terms = suggestion["value"].split(" ");
	window.location = "Movie?title=" + query_terms.join("+");
	return false;
}

$('#autocomplete').autocomplete({
    lookup: function (query, doneCallback) {
    		handleLookup(query, doneCallback)
    },
    onSelect: function(suggestion) {
    		handleSelectSuggestion(suggestion)
    },
    groupBy: "category",
    deferRequestBy: 300,
    minChars: 3
});


function handleNormalSearch(query) {
	query_terms = query.split(" ");
	window.location = "MovieList?title=" + query_terms.join("+");
}

$('#autocomplete').keypress(function(event) {
	if (event.keyCode == 13) {
		handleNormalSearch($('#autocomplete').val())
	}
})

$('#searchButton').click(function(event) {
	handleNormalSearch($('#autocomplete').val())
})

