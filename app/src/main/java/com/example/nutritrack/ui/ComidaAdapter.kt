package com.example.nutritrack.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritrack.R
import com.example.nutritrack.data.model.ComidaConDetalles

class ComidaAdapter(private val comidas: List<ComidaConDetalles>) :
    RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>() {

    class ComidaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreAlimento: TextView = view.findViewById(R.id.textNombreAlimento)
        val cantidad: TextView = view.findViewById(R.id.textCantidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comida, parent, false)
        return ComidaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = comidas[position]
        val detalles = comida.DetalleComidas.joinToString(", ") { "${it.Alimento.nombre} x${it.cantidad}g" }
        holder.nombreAlimento.text = detalles

        // Calcular totales
        val totales = calcularTotales(comida)
        val resultado = """
            Calorías: ${"%.2f".format(totales["Calorías"])} kcal
            Proteínas: ${"%.2f".format(totales["Proteínas"])} g
            Carbohidratos: ${"%.2f".format(totales["Carbohidratos"])} g
            Grasas: ${"%.2f".format(totales["Grasas"])} g
        """.trimIndent()
        holder.cantidad.text = resultado
    }

    override fun getItemCount(): Int = comidas.size

    fun calcularTotales(comida: ComidaConDetalles): Map<String, Double> {
        var totalCalorias = 0.0
        var totalProteinas = 0.0
        var totalCarbohidratos = 0.0
        var totalGrasas = 0.0

        comida.DetalleComidas.forEach { detalle ->
            val cantidad = detalle.cantidad.toDouble()
            val alimento = detalle.Alimento

            totalCalorias += (cantidad * alimento.caloriasPorPorcion) / 100
            totalProteinas += (cantidad * alimento.proteinaPorPorcion) / 100
            totalCarbohidratos += (cantidad * alimento.carbohidratosPorPorcion) / 100
            totalGrasas += (cantidad * alimento.grasaPorPorcion) / 100
        }

        return mapOf(
            "Calorías" to totalCalorias,
            "Proteínas" to totalProteinas,
            "Carbohidratos" to totalCarbohidratos,
            "Grasas" to totalGrasas
        )
    }
}