package resources;

public enum APIResources {
	
	GetDetailsAPI("/v1/Categories/6327/Details.json?catalogue=false");
	private String resource;
	
	APIResources(String resource){
		this.resource=resource;
		
	}

	public String getResource() {
		return resource;
	}
}
