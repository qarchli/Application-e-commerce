let clientForm = document.getElementById("clientForm");
let clientSelect = document.getElementById("clientSelect");
let oui = document.getElementById('oui');
let non = document.getElementById('non');
let radios = document.getElementsByName('choixNouveauClient');

for (let i = 0; i < radios.length; i++) {
	radios[i].onclick = function() {
		if (oui.checked && !non.checked) {
			show(clientForm);
			hide(clientSelect);
	        document.getElementById('listeClients').selectedIndex = '0';			
		} else if (!oui.checked && non.checked) {
			hide(clientForm);
			show(clientSelect);
		}
	}
}

function show(element) {
	element.style.display = 'block';
}
function hide(element) {
	element.style.display = 'none';
}
