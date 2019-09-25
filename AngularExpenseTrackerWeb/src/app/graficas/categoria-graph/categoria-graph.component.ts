//import { Component, OnInit } from '@angular/core';
import { Component, OnInit, OnChanges, ViewChild, ElementRef, Input, ViewEncapsulation, AfterViewInit } from '@angular/core';
import { GraphDataService } from '../../services/graphdata.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

import * as d3 from 'd3';
import * as $ from 'jquery';

@Component({
  selector: 'app-categoria-graph',
  templateUrl: './categoria-graph.component.html',
  styleUrls: ['./categoria-graph.component.css']
})
export class CategoriaGraphComponent implements OnInit, AfterViewInit, OnChanges {
    
    @ViewChild('chart',{static: false}) private chartContainer: ElementRef;

    public data = [ ];
    private margin: any = { top: 20, bottom: 20, left: 50, right: 20};
    private chart: any;
    private width: number;
    private height: number;
    private xScale: any;
    private yScale: any;
    private colors: any;
    private xAxis: any;
    private yAxis: any;
    
    public listaLimite = [ 6,12,24,36,48,60];
    public listaYears = [ 0 ];
    
    
    datosForma = this.fb.group({
       limiteSeleccionado: [],
        yearSeleccionado: []
    });
    
    constructor(private graphService:GraphDataService,private fb: FormBuilder,) { }
    
    ngOnInit() {
        this.datosForma.controls.limiteSeleccionado.setValue(12);
        this.datosForma.controls.yearSeleccionado.setValue(0);
        
        for( var i = 0; i < 10; i++ )
            this.listaYears.push(new Date().getFullYear()-i);
    }
    
    ngOnChanges() {
        if (this.chart) {
          this.updateChart();
        }
      }
    
    ngAfterViewInit() {
        //this.createChart();
        this.graphService.getTransactionsSumByMonth(12,0).subscribe(res=>this.graphdataResponse(res));
    }
    
    graphdataResponse(res:any) {
        if( res == undefined || res == null || res.success == false )
            return;
        
        this.data = res.data;
        
        //invertir resultados para ordenar de la fecha menos reciente a la mas reciente
        this.data.reverse();
        
        for( var i = 0; i < this.data.length; i++ )
            this.data[i].value = this.data[i].value / 100;
        
        //crear grafica
        this.createChart();
    }
    
    generateData() {
        //this.chartData = [];
        for (let i = 0; i < (8 + Math.floor(Math.random() * 10)); i++) {
        //this.chartData.push(['Index' ${i},Math.floor(Math.random() * 100)]);
       }
     }
    
    createChart() {

        let element = this.chartContainer.nativeElement;
        $(element).html("");
        this.width = element.offsetWidth - this.margin.left - this.margin.right;
        this.height = element.offsetHeight - this.margin.top - this.margin.bottom;
     
        let svg = d3.select(element).append('svg').attr('width', element.offsetWidth).attr('height', element.offsetHeight);
        
        // chart plot area
        this.chart = svg.append('g').attr('class', 'bars').attr('transform', "translate(" +this.margin.left + "," + this.margin.top + ")");

        // define X & Y domains
        let xDomain = this.data.map(d => d.label);//this.data.map(d => d[0]);
        let yDomain = [0, d3.max(this.data,d=> d.value)];

        // create scales
        this.xScale = d3.scaleBand().padding(0.1).domain(xDomain).rangeRound([0, this.width]);
        this.yScale = d3.scaleLinear().domain(yDomain).range([this.height, 0]);

        // x & y axis
        this.xAxis = svg.append('g')
          .attr('class', 'axis axis-x')
          .attr('transform', `translate(${this.margin.left}, ${this.margin.top + this.height})`)
          .call(d3.axisBottom(this.xScale));
        
        this.yAxis = svg.append('g')
          .attr('class', 'axis axis-y')
          .attr('transform', `translate(${this.margin.left}, ${this.margin.top})`)
          .call(d3.axisLeft(this.yScale).tickFormat(function(d){
                                                      var value:number = parseInt(d.toString());
                                                      if( value <= 999 )
                                                          return value.toString();
                                                      
                                                      if( value <= 999999 ) {
                                                          value = value / 1000;
                                                          return value+"K";
                                                      }
                                                      //dividir entre 1 millon
                                                      value = value / 1000000;
                                                      return value.toFixed(2)+"M";
                                              }));
        
        this.chart.selectAll()
        .data(this.data)
        .enter()
        .append('rect')
        .attr('x', (s) => this.xScale(s.label))
        .attr('y', (s) => this.yScale(s.value))
        .attr('height', (s) => this.height - this.yScale(s.value) )
        .attr('width', this.xScale.bandwidth())
        .style('fill', (d, i) => '#ff9300')
        .on("mouseover",function(d) {
                $("#aux").text("Fecha:"+d.label+" Valor:"+d.value.toLocaleString());
                $("#aux").css("left",(d3.event.pageX-100)+"px");
                $("#aux").css("top",(d3.event.pageY-28)+"px");
                $("#aux").show();
                })
        .on("mouseout",function() {$("#aux").hide();});
        
      }

      updateChart() {
          
        // update scales & axis
        this.xScale.domain(this.data.map(d => d[0]));
        this.yScale.domain([0, d3.max(this.data, d => d[1])]);
        this.colors.domain([0, this.data.length]);
        this.xAxis.transition().call(d3.axisBottom(this.xScale));
        this.yAxis.transition().call(d3.axisLeft(this.yScale));

        let update = this.chart.selectAll('.bar')
          .data(this.data);

        // remove exiting bars
        update.exit().remove();

        // update existing bars
        this.chart.selectAll('.bar').transition()
          .attr('x', d => this.xScale(d[0]))
          .attr('y', d => this.yScale(d[1]))
          .attr('width', d => this.xScale.bandwidth())
          .attr('height', d => this.height - this.yScale(d[1]))
          .style('fill', (d, i) => "#ff9300");

        // add new bars
        update
          .enter()
          .append('rect')
          .attr('class', 'bar')
          .attr('x', d => this.xScale(d[0]))
          .attr('y', d => this.yScale(0))
          .attr('width', this.xScale.bandwidth())
          .attr('height', 0)
          .style('fill', (d, i) => this.colors(i))
          .transition()
          .delay((d, i) => i * 10)
          .attr('y', d => this.yScale(d[1]))
          .attr('height', d => this.height - this.yScale(d[1]));
      }
      
      actualizarGrafica() {
          $("#tabgraph").addClass("active");
          $("#tabgraphparams").removeClass("active");
          $("#navtabgraph").addClass("active");
          $("#navtabgraphparams").removeClass("active");
          this.graphService.getTransactionsSumByMonth(this.datosForma.value.limiteSeleccionado,this.datosForma.value.yearSeleccionado).subscribe(res=>this.graphdataResponse(res));
      }
}