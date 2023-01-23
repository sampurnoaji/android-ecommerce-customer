package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.model.request.address.AddAddressRequest
import id.io.android.olebsai.data.source.remote.address.AddressRemoteDataSource
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.repository.AddressRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(private val remoteDataSource: AddressRemoteDataSource) :
    AddressRepository, ResponseHelper() {

    override suspend fun getAddressList(): LoadState<List<Address>> {
        return map { remoteDataSource.getAddressList().toAddressList() }
    }

    override suspend fun setAddressDefault(addressId: Int): LoadState<Any> {
        return map { remoteDataSource.setAddressDefault(addressId) }
    }

    override suspend fun addAddress(address: Address): LoadState<Any> {
        return map {
            remoteDataSource.addAddress(
                AddAddressRequest(
                    address = address.address,
                    name = address.name,
                    phone = address.phone,
                    label = address.label,
                    note = address.note,
                )
            )
        }
    }

    override suspend fun updateAddress(address: Address): LoadState<Any> {
        return map {
            remoteDataSource.updateAddress(
                addressId = address.id,
                request = AddAddressRequest(
                    address = address.address,
                    name = address.name,
                    phone = address.phone,
                    label = address.label,
                    note = address.note,
                ),
            )
        }
    }

    override suspend fun deleteAddress(addressId: Int): LoadState<Any> {
        return map { remoteDataSource.deleteAddress(addressId) }
    }
}