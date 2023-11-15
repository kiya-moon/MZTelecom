/*구매후기 카테고리*/

var customSelect = document.querySelector(".rev-select");
var selectItems = document.querySelector(".rev-select-items");

customSelect.addEventListener("click", function() {
	selectItems.classList.toggle("rev-select-items-active");
});

var options = selectItems.querySelectorAll("div");
options.forEach(function(option) {
	option.addEventListener("click", function() {
		var selectedValue = option.textContent;
		customSelect.querySelector(".rev-select-selected").textContent = selectedValue;
	});
});