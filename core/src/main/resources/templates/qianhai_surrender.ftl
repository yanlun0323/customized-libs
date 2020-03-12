<?xml version="1.0" encoding="UTF-8"?>
<Packet type="REQUEST" version="1.0">
	<Head>
		<UUID>${UUID}</UUID>
		<CRequestType>170002</CRequestType>
		<CBusiChnl>kayou</CBusiChnl>
		<TAcctTm>${TAcctTm}</TAcctTm>
		<#if CheckCode?exists>
		<CheckCode>${CheckCode}</CheckCode>
		</#if>
	</Head>
	<Body>
		<CProdNo>150007</CProdNo>
		<CancleDate>${CancleDate}</CancleDate>
		<EdrList>
			<Edr>
				<PlyNo>${PlyNo}</PlyNo>
			</Edr>
		</EdrList>
	</Body>
</Packet>