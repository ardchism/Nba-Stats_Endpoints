package com.nbastat.player.domain.builders;

import java.util.List;

import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatHeaders;

public class NBAStatHeadersBuilder {

	private NBAStatHeaders builtObject; 
	
	public static NBAStatHeadersBuilder builder() {
		
		return new NBAStatHeadersBuilder();
	
	}
	
	public NBAStatHeadersBuilder build() {

		builtObject = new NBAStatHeaders();	
		return this;
	
	}
	
	public NBAStatHeadersBuilder withHost(String host) {
		
		builtObject.setHost(host);
		return this;
		
	}
	
	public NBAStatHeadersBuilder withReferer(String referer) {
		
		builtObject.setReferer(referer);
		return this;
		
	}
	
	public NBAStatHeadersBuilder withUserAgent(String userAgent) {
		
		builtObject.setUserAgent(userAgent);
		return this;
		
	}
	
	public NBAStatHeadersBuilder withXnbastatsOrigin(String xnbastatsOrigin) {
		
		builtObject.setXnbastatsOrigin(xnbastatsOrigin);
		return this;
		
	}
	
	public NBAStatHeadersBuilder withXnbastatsToken(String xnbastatsToken) {
		
		builtObject.setXnbastatsToken(xnbastatsToken);
		return this;
		
	}
	
	public NBAStatHeaders get() {
	
		return builtObject;
	
	}
	
}
