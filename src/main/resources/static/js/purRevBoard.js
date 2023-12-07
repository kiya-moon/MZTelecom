/*구매후기 카테고리*/

var revSelect = document.querySelector(".rev-select");
var revItems = document.querySelector(".rev-select-items");
var selectedCategoryInput = document.getElementById("selectedCategory");


revSelect.addEventListener("click", function() {
	revItems.classList.toggle("rev-select-items-active");
});

var options = revItems.querySelectorAll("div");
options.forEach(function(option) {
	option.addEventListener("click", function() {
		var selectedValue = option.textContent;
		revSelect.querySelector(".rev-select-selected").textContent = selectedValue;
	
		selectedCategoryInput.value = selectedValue;
	});
});


/*검색시*/
function updatePage(page) {
    var keyword = document.getElementById('keyWord').value;
    var catgo = document.getElementById('catgo').value;
    var url = '/purRevBoard/';
    
    if (keyword) {
        url += '?page=' + page + '&catgo=' + catgo + '&keyWord=' + keyword;
    } else {
        url += '?page=' + page;
    }

    window.location.href = url;
}



