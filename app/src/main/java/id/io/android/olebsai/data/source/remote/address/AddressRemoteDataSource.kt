package id.io.android.olebsai.data.source.remote.address

import id.io.android.olebsai.data.model.request.address.AddAddressRequest
import id.io.android.olebsai.data.model.response.address.AddressListResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class AddressRemoteDataSource @Inject constructor(private val service: AddressService) :
    ResponseHelper() {

    suspend fun getAddressList(): AddressListResponse = call { service.getAddressList().data!! }
    suspend fun setAddressDefault(addressId: Int): Any =
        call { service.setAddressDefault(addressId) }

    suspend fun addAddress(request: AddAddressRequest): Any = call { service.addAddress(request) }
    suspend fun updateAddress(request: AddAddressRequest, addressId: Int): Any =
        call { service.updateAddress(request, addressId) }

    suspend fun deleteAddress(addressId: Int): Any = call { service.deleteAddress(addressId) }
}