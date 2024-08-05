package com.nonreactive.activerecord.resources;

import com.nonreactive.activerecord.entity.Customer;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("customers")
public class CustomerResource {
    @GET
    public List<Customer> findAll() {
        return Customer.listAll();
    }
    @GET
    @Path("{id}")
    public Customer findById(@PathParam("id") Integer id) {
        Customer customer = Customer.findById(id);
        if (customer == null) {
            throw new WebApplicationException("Customer with Id of " + id + " does not exists");
        }
        return customer;
    }

    @POST
    @Transactional
    public Response create(Customer customer) {
        if (customer.id != null) {
            throw new WebApplicationException("Invalid Data", 422);
        }
        customer.persist();
        return Response.status(201).entity(customer).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Customer update(@PathParam("id") Long id, Customer customer) {
        Customer customerEntity = Customer.findById(id);
        if (customerEntity == null) {
            throw new WebApplicationException("Customer with Id of " + id + " does not exists");
        }
        if (customer.name == null) {
            throw new WebApplicationException("Customer payload  does not exists");
        }

        customerEntity.name = customer.name;
        customerEntity.city = customer.city;
        return customerEntity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response remove(@PathParam("id") Long id) {
        Customer customerEntity = Customer.findById(id);
        if (customerEntity == null) {
            throw new WebApplicationException("Customer with Id of " + id + " does not exists");
        }
        customerEntity.delete();
        return Response.status(204).build();
    }
}
