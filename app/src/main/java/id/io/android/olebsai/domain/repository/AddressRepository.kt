package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.util.LoadState

interface AddressRepository {
    suspend fun getAddressList(): LoadState<List<Address>>
    suspend fun setAddressDefault(addressId: Int): LoadState<Any>
    suspend fun addAddress(address: Address): LoadState<Any>
    suspend fun updateAddress(address: Address): LoadState<Any>
    suspend fun deleteAddress(addressId: Int): LoadState<Any>
}