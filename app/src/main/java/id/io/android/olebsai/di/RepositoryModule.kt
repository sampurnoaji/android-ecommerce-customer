package id.io.android.olebsai.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.io.android.olebsai.data.repository.AddressRepositoryImpl
import id.io.android.olebsai.data.repository.ProductRepositoryImpl
import id.io.android.olebsai.data.repository.UserRepositoryImpl
import id.io.android.olebsai.domain.repository.AddressRepository
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.domain.repository.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindsProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    fun bindsAddressRepository(repositoryImpl: AddressRepositoryImpl): AddressRepository
}