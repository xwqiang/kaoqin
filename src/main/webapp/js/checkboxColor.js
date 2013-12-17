function extQuery(serverId,customer_sn,td_code) {
	window.open(
					'extQuery.do?td_code=' + td_code+'&customer_sn='+ customer_sn+'&server_id='+ serverId,
					'newwindow',
					'height=600,width=450,top=200,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no, status=no')
}
