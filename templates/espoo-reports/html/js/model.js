function setRootPanelHeight() {
	$('.root-panel-body').css('height', $('.root-panel').outerHeight() - $('.root-panel-heading').outerHeight());
}

function strcmp(a, b) {
	// Muokattu Espoon tiedonhallintamallia varten huomioimaan prosessien tai tehtäväluokkien tunnisteet merkkijonon alussa, jos on
	var aText = $(a).text().trim();
	var bText = $(b).text().trim();
	var tunnistePattern = /^([OT]|Y[AJP]?)?(\d+\.(\d+|\d+(\.\d+)*)?)($|[^0-9\.])/;
	var aMatch = tunnistePattern.exec(aText);
	var bMatch = tunnistePattern.exec(bText);
	if (!aMatch && !bMatch) {  // kumpikaan ei ole tunnisteellinen
		aText = aText.toLowerCase();
		bText = bText.toLowerCase();
		if (aText.toString() < bText.toString()) return -1;
		if (aText.toString() > bText.toString()) return 1;
		return 0;
	}
	if (!bMatch) return -1; // a on tunnisteellinen, b ei, tunnisteelliset ensin
	if (!aMatch) return 1;  // b on tunnisteellinen, a ei, tunnisteelliset ensin
	var aParts = aMatch[2].split(".");
	var bParts = bMatch[2].split(".");
	var i = 0;
	while (i < aParts.length && i < bParts.length) {
		if (parseInt(aParts[i]) > parseInt(bParts[i])) return 1;
		if (parseInt(aParts[i]) < parseInt(bParts[i])) return -1;
		i++;
	}
	if (aParts.length > i) return 1;
	if (bParts.length > i) return -1;
	return 0;
}

$(document).ready(function() {
	// Set jQuery UI Layout panes
  $('body').layout({
    minSize: 50,
    maskContents: true,
    north: {
      size: 55,
      spacing_open: 8,
      closable: false,
      resizable: false
    },
    west: {
			size: 350,
			spacing_open: 8
		},
    west__childOptions: {
      maskContents: true,
      south: {
	      minSize: 50,
				size: 250,
				spacing_open: 8
			},
			center: {
				minSize: 50,
				onresize: "setRootPanelHeight"
			}
    }
  });
	
	// Set heigh of panels the first time
	setRootPanelHeight();
	
	// Setup modeltree
	$('.tree li:has(ul)').addClass('parent_li').find(' > ul > li').hide();

	// Add show/hide function on modeltree
	$('.tree li.parent_li > span').on('click', function (e) {
		var children = $(this).parent('li.parent_li').find(' > ul > li');
		if (children.is(":visible")) {
			children.hide('fast');
			$(this).find(' > i').addClass('glyphicon-triangle-right').removeClass('glyphicon-triangle-bottom');
		} else {
			// START SORT
			$(this).parent('li.parent_li').find(' > ul').each(function(index){
				$(this).children('li.tree-folder').sort(strcmp).appendTo($(this));
				$(this).children('li.tree-element').sort(strcmp).appendTo($(this));
			});
			// END SORT
			children.show('fast');
			$(this).find(' > i').addClass('glyphicon-triangle-bottom').removeClass('glyphicon-triangle-right');
		}
		e.stopPropagation();
	});
});
