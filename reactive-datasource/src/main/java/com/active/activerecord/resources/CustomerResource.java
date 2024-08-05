package com.active.activerecord.resources;

import com.active.activerecord.entity.Customer;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("customers")
public class CustomerResource {

    @GET
    public Uni<List<Customer>> findAll() {
        return Customer.listAll();
    }

    @Path("{id}")
    @GET
    public Uni<Response> findById(@PathParam("id") Long id) {
        return Customer.findById(id).onItem().transform(entity -> {
            if (entity == null) {
                throw new WebApplicationException("Customer Not found");
            }
            return Response.ok(entity).build();
        });
    }

    @POST
    public Uni<Response> create(Customer customer) {
        if (customer == null) {
            throw new WebApplicationException("Customer Not found");

        }
        return Panache.withTransaction(customer::persist)
                .replaceWith(Response.status(201).entity(customer).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(@PathParam("id") Long id, Customer customer) {
        if (customer == null) {
            throw new WebApplicationException("Customer Not Found");
        }
        return Panache.withTransaction(() -> {
            return Customer.<Customer>findById(id)
                    .onItem()
                    .ifNotNull()
                    .invoke(entity -> {
                        //update logic
                        entity.city = customer.city;
                        entity.name = customer.name;
                    })
                    .onItem()
                    .ifNotNull()
                    .transform(entity -> Response.ok(entity).status(200).build())
                    .onItem()
                    .ifNull()
                    .continueWith(Response.status(Response.Status.NOT_FOUND)::build);
        });
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return Panache.withTransaction(() ->
                Customer.deleteById(id).map(isDeleted -> isDeleted ? Response.ok().status(Response.Status.NO_CONTENT).build()
                        : Response.status(Response.Status.NOT_FOUND).build()
                )
        );
    }

}
