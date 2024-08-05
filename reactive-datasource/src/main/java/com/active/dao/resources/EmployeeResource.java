package com.active.dao.resources;

import com.active.dao.enity.Employee;
import com.active.dao.repository.EmployeeRespository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("employees")
public class EmployeeResource {

    @Inject
    EmployeeRespository respository;

    @GET
    public Uni<List<Employee>> findAll() {
        return respository.listAll();
    }

    @Path("{id}")
    @GET
    public Uni<Response> findById(@PathParam("id") Long id) {
        return respository.findById(id).onItem().transform(entity -> {
            if (entity == null) {
                throw new WebApplicationException("Entity Not found");
            }
            return Response.ok(entity).build();
        });
    }

    //save
    @POST
    @WithTransaction
    public Uni<Response> create(Employee employee) {
        //handle error
        if (employee == null || employee.name == null) {
            throw new WebApplicationException("Customer Not Found", 400);
        }
        return respository.persist(employee).onItem().transform(entity ->
                Response.status(201).entity(entity).build()
        );
    }

    //update
    @PUT
    @WithTransaction
    @Path("{id}")
    public Uni<Response> update(@PathParam("id") Long id, Employee employee) {
        if (employee == null || employee.name == null) {
            throw new WebApplicationException("Customer Not Found", 400);
        }
        String query = "city='" + employee.getCity() + "' WHERE id=?1";
        System.out.println(query);
        return respository.update(query, id).onItem().transform(entity ->
                Response.ok().status(200).entity(entity).build());
    }

    //delete
    @Path("{id}")   
    @DELETE
    @WithTransaction
    public Uni<Response> delete(@PathParam("id") Long id) {
        return respository.deleteById(id).onItem().transform(isDeleted ->
                isDeleted ? Response.ok().status(Response.Status.NO_CONTENT).build()
                        : Response.ok().status(Response.Status.NOT_FOUND).build()
        );
    }


}
