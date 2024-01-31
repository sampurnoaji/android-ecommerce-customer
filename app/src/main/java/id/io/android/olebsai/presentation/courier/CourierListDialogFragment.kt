package id.io.android.olebsai.presentation.courier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.io.android.olebsai.databinding.FragmentCourierListDialogListDialogBinding
import id.io.android.olebsai.databinding.FragmentCourierListDialogListDialogItemBinding
import id.io.android.olebsai.domain.model.basket.Courier
import id.io.android.olebsai.domain.model.basket.Courier.Service
import id.io.android.olebsai.util.obtainParcelableArrayList
import id.io.android.olebsai.util.toRupiah

const val ARG_COURIERS = "couriers"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    CourierListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class CourierListDialogFragment(
    private val listener: Listener
) : BottomSheetDialogFragment() {

    private var _binding: FragmentCourierListDialogListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCourierListDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            adapter =
                arguments?.obtainParcelableArrayList<Courier>(ARG_COURIERS)?.let {
                    val item = mutableListOf<Item>()
                    it.forEach { courier ->
                        courier.services.mapIndexed { index, service ->
                            item.add(
                                Item(
                                    service = service,
                                    id = courier.kurirId + index,
                                    name = courier.namaKurir
                                )
                            )
                        }
                    }
                    ItemAdapter(item)
                }
        }
    }

    private inner class ViewHolder(val binding: FragmentCourierListDialogListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    private inner class ItemAdapter(private val items: MutableList<Item>) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                FragmentCourierListDialogListDialogItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder.binding) {
                tvPrice.text = items[position].service.ongkir.toRupiah()
                tvName.text = items[position].name
                tvService.text = items[position].service.namaServis
                tvCourierEstimate.text = "${items[position].service.estimasiSampai} hari"
                root.setOnClickListener {
                    listener.onCourierSelected(items[position])
                    dismiss()
                }
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }

    companion object {
        fun newInstance(couriers: ArrayList<Courier>, listener: Listener): CourierListDialogFragment =
            CourierListDialogFragment(listener).apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_COURIERS, couriers)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface Listener {
        fun onCourierSelected(item: Item)
    }

    data class Item(
        val id: String,
        val name: String,
        val service: Service,
    )
}