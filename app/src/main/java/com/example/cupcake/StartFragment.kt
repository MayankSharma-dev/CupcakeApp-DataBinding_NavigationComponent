
package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding
import com.example.cupcake.model.OrderViewModel

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */
class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding:FragmentStartBinding get() = _binding!!

    // activityViewModels() gives you the ViewModel instance scoped to the current activity.
    // Therefore the instance will remain the same across multiple fragments in the same activity.
    private val sharedViewModel:OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(layoutInflater,container,false)
        //_binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  apply is a scope function in the Kotlin standard library.
            It executes a block of code within the context of an object.
            It forms a temporary scope, and in that scope, you can access the object without its name.
            The common use case for apply is to configure an object. Such calls can be read as "apply the following assignments to the object."
         */
        binding?.apply {
            // Set up the button click listeners
            orderOneCupcake.setOnClickListener { orderCupcake(1) }
            orderSixCupcakes.setOnClickListener { orderCupcake(6) }
            orderTwelveCupcakes.setOnClickListener { orderCupcake(12) }
        }
    }

    /**
     * Start an order with the desired quantity of cupcakes and navigate to the next screen.
     */
    private fun orderCupcake(quantity: Int) {
        sharedViewModel.setQuantity(quantity)

        // setting the default flavour of flavours radio.
         if(sharedViewModel.hasNoFlavorSet()){
            sharedViewModel.setFlavour(getString(R.string.vanilla))
        }
        val action = StartFragmentDirections.actionStartFragmentToFlavorFragment()
        findNavController().navigate(action)
//        Toast.makeText(activity, "Ordered $quantity cupcake(s)", Toast.LENGTH_SHORT).show()
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}