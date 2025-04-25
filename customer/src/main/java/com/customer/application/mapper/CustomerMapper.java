package com.customer.application.mapper;

import java.util.Optional;

import com.commons.dto.request.SaveCustomer;
import com.commons.dto.response.GetAddress;
import com.commons.dto.response.GetCustomerDetail;
import com.customer.application.dto.request.UpdateAddress;
import com.customer.application.dto.request.UpdateCustomer;
import com.customer.application.dto.response.GetCustomer;
import com.customer.domain.persistence.Address;
import com.customer.domain.persistence.Customer;

public class CustomerMapper {

        public static GetCustomerDetail toDtoFCustomerDetail(Customer customer) {
                if (customer == null) {
                        return null;
                }
                return new GetCustomerDetail(
                                customer.getId(),
                                customer.getName(),
                                customer.getLastname(),
                                customer.getEmail(),
                                customer.getMobile(),
                                CustomerMapper.toDtoFromAddress(customer.getAddress()),
                                customer.getCreatedAt(),
                                customer.getDeletedAt());
        }

        public static GetCustomer toDtoFromEntity(Customer customer) {
                if (customer == null) {
                        return null;
                }
                return new GetCustomer(
                                customer.getId(),
                                customer.getName(),
                                customer.getLastname(),
                                customer.getEmail(),
                                customer.getMobile());
        }

        public static Customer toEntityFromDto(SaveCustomer saveCustomer) {
                if (saveCustomer == null) {
                        return null;
                }

                SaveCustomer.SaveAddress saveAddress = saveCustomer.address();

                Address address = Address.builder()
                                .country(saveAddress.country())
                                .state(saveAddress.state())
                                .city(saveAddress.city())
                                .street(saveAddress.street())
                                .streetNumber(saveAddress.streetNumber())
                                .apartment(saveAddress.apartment())
                                .floor(saveAddress.floor())
                                .additionalInfo(saveAddress.additionalInfo())
                                .postalCode(saveAddress.postalCode())
                                .build();

                return Customer.builder()
                                .name(saveCustomer.name())
                                .lastname(saveCustomer.lastName())
                                .email(saveCustomer.email())
                                .mobile(saveCustomer.mobile())
                                .address(address)
                                .isActive(true)
                                .build();
        }

        public static GetAddress toDtoFromAddress(Customer customer) {
                if (customer == null)
                        return null;
                return toDtoFromAddress(customer.getAddress());
        }

        public static GetAddress toDtoFromAddress(Address address) {
                if (address == null)
                        return null;
                return new GetAddress(
                                address.getCountry(),
                                address.getState(),
                                address.getCity(),
                                address.getPostalCode(),
                                address.getStreet(),
                                address.getStreetNumber(),
                                address.getApartment(),
                                address.getFloor(),
                                address.getAdditionalInfo());
        }

        public static void updateCustomerEntityFromDto(UpdateCustomer updateCustomer, Customer oldCustomer) {
                if (updateCustomer == null || oldCustomer == null)
                        return;
                Optional.ofNullable(updateCustomer.name())
                                .ifPresent(oldCustomer::setName);

                Optional.ofNullable(updateCustomer.lastName())
                                .ifPresent(oldCustomer::setLastname);

                Optional.ofNullable(updateCustomer.email())
                                .ifPresent(oldCustomer::setEmail);

                Optional.ofNullable(updateCustomer.mobile())
                                .ifPresent(oldCustomer::setMobile);
        }

        public static void updateAddressFromDto(UpdateAddress updateAddress, Address oldAddress) {
                if (updateAddress == null || oldAddress == null)
                        return;
                Optional.ofNullable(updateAddress.country())
                                .ifPresent(oldAddress::setCountry);

                Optional.ofNullable(updateAddress.state())
                                .ifPresent(oldAddress::setState);

                Optional.ofNullable(updateAddress.city())
                                .ifPresent(oldAddress::setCity);

                Optional.ofNullable(updateAddress.street())
                                .ifPresent(oldAddress::setStreet);

                Optional.ofNullable(updateAddress.streetNumber())
                                .ifPresent(oldAddress::setStreetNumber);

                Optional.ofNullable(updateAddress.apartment())
                                .ifPresent(oldAddress::setApartment);

                Optional.ofNullable(updateAddress.floor())
                                .ifPresent(oldAddress::setFloor);

                Optional.ofNullable(updateAddress.additionalInfo())
                                .ifPresent(oldAddress::setAdditionalInfo);

        }

}
