package org.xtimms.ridebus.ui.routes.express

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import org.xtimms.ridebus.R
import org.xtimms.ridebus.data.database.entity.Route

//
// Created by Xtimms on 28.08.2021.
//
class ExpressItem(val route: Route) : AbstractFlexibleItem<ExpressHolder>() {

    override fun equals(other: Any?): Boolean {
        if (other is ExpressItem) {
            return route.routeId == other.route.routeId
        }
        return false
    }

    override fun hashCode(): Int {
        return route.routeId!!.hashCode()
    }

    override fun getLayoutRes(): Int {
        return R.layout.routes_item
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>
    ): ExpressHolder {
        return ExpressHolder(view, adapter as ExpressAdapter)
    }

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>,
        holder: ExpressHolder,
        position: Int,
        payloads: List<Any?>?
    ) {
        holder.bind(this)
    }
}
