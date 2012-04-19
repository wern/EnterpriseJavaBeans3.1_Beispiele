package kochrezepte.restful;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("Kunde")
public interface KundenManagement {
	@POST
	@Produces("application/xml")
	public Kunde erzeuge(@QueryParam("name") String name,
			@QueryParam("kdnr") int kundenNummer);

	@GET
	@Path("{id}")
	@Produces("application/xml")
	public Kunde finde(@PathParam("id") int kundenNummer);

	@DELETE
	@Path("{id}")
	@Produces("application/xml")
	public void loesche(@PathParam("id") int kundenNummer);
}
