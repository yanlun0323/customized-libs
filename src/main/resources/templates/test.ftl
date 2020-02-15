<?xml version="1.0" encoding="UTF-8"?>
<Packet type="REQUEST" version="1.0">
	<Head>
		<UUID>${UUID}</UUID>
		<CRequestType>170001</CRequestType>
		<CBusiChnl>kayou</CBusiChnl>
		<#if CheckCode?exists>
		<CheckCode>${CheckCode}</CheckCode>
		</#if>
	</Head>
	<Body>
		<Base>
			<CProdNo>150007</CProdNo>
			<NAmt>${NAmt?c}</NAmt>
			<NPrm>${NPrm?c}</NPrm>
		</Base>
		<Applicant>
			<CAppNme>${CAppNme}</CAppNme>
			<CClntMrk>${CClntMrk}</CClntMrk>
		</Applicant>
	</Body>
</Packet>