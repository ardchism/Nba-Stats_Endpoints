package com.nbastat.player.generators.domain.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition;
import com.nbastat.player.utils.XmlReader;

import lombok.SneakyThrows;

public class NBAStatEndpointsDefinitionFactory {

	@SneakyThrows
	public NBAStatEndpointsDefinition buildFromJsonFile(String fileName) {
		
		XmlReader xmlReader = new XmlReader();
		String nbaStatJson = xmlReader.readXmlFromFile(fileName);
		ObjectMapper mapper = new ObjectMapper();
		NBAStatEndpointsDefinition nbaStatEndpointsDefinition = mapper.readValue(nbaStatJson, NBAStatEndpointsDefinition.class);
		return nbaStatEndpointsDefinition;
	}
}
