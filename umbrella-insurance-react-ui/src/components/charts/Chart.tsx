import '../../css/apps/App.css';
import React from 'react';
import { useEffect, useState } from 'react';

export default function Chart() {
    const [canvasWidth, updateCanvasWidth] = useState(400);
    const [canvasHeight, updateCanvasHeight] = useState(400);
    const [padding, updatePadding] = useState(75);
    const [graphWidth, updateGraphWidth] = useState(canvasWidth - (2 * padding));
    const [graphHeight, updateGraphHeight] = useState(canvasHeight - (2 * padding));
    const [xOrigin, updateXOrigin] = useState((graphWidth / 2) + padding);
    const [yOrigin, updateYOrigin] = useState(graphHeight + padding);
    const [xMin, updateXMin] = useState(padding);
    const [xMax, updateXMax] = useState(padding + graphWidth);
    const [yMin, updateYMin] = useState(padding);
    const [yMax, updateYMax] = useState(padding + graphHeight);
    const [xValuePerTick, updateXValuePerTick] = useState(1);
    const [yValuePerTick, updateYValuePerTick] = useState(0.25);
    let lightGray = "rgba(0,0,0,0.1)";
    let white = "rgba(255,255,255,1)";
    let homeColor = "rgba(0,255,0,0.5)";
    let awayColor = "rgba(255,0,0,0.5)";
    let homeColorMatching = "rgba(0,125,0,0.5)";
    let awayColorMatching = "rgba(125,0,0,0.5)";
    const home = "home";
    const away = "away";
    const [gridColor, updateGridColor] = useState(lightGray);
    const [graphColor, updateGraphColor] = useState(white);
    const [arrowLength, updateArrowLength] = useState(graphWidth / 100);
    const [tickMarkHeight, updateTickMarkHeight] = useState(3);
    const [numberOfTickMarks, updateNumberOfTickMarks] = useState(20);
    const [distanceBetweenXTickMarks, updateDistanceBetweenXTickMarks] = useState(graphWidth / numberOfTickMarks);
    const [distanceBetweenYTickMarks, updateDistanceBetweenYTickMarks] = useState(graphHeight / numberOfTickMarks);
    const [r, updateR] = useState(3);
    const [startAngle, updateStartAngle] = useState(0);
    const [endAngle, updateEndAngle] = useState(2 * Math.PI)
    const [counterClockwise, updateCounterClockwise] = useState(true);

    function getContext() : CanvasRenderingContext2D | null {
        let c : HTMLCanvasElement = document.getElementById("myCanvas") as HTMLCanvasElement;
        let ctx : CanvasRenderingContext2D | null = c?.getContext("2d");
        return ctx;
    }

    function graphBackground(ctx : CanvasRenderingContext2D) {
        ctx.beginPath();
        ctx.fillStyle = graphColor;
        ctx.fillRect(padding, padding, graphWidth, graphHeight);
        ctx.stroke();
    }

    function graphOuterBackground(ctx : CanvasRenderingContext2D) {
        ctx.beginPath();
        ctx.fillStyle = "rgba(255,255,255,1)";
        ctx.fillRect(0, 0, canvasWidth, canvasHeight);
        ctx.stroke();
    }

    function graphXAxis(ctx : CanvasRenderingContext2D,
         arrowLength: number, tickMarkHeight: number,
          numberOfTickMarks : number, distanceBetweenTickMarks : number) {
        graphXAxisLine(ctx)
        //left arrow
        graphLeftArrow(ctx, arrowLength, xMin, yOrigin);
        //right arrow
        graphRightArrow(ctx, arrowLength, xMax, yOrigin);
        //x-axis tick marks
        graphXAxisTickMarks(ctx, tickMarkHeight, numberOfTickMarks, distanceBetweenTickMarks);
    }

    function graphXAxisLine(ctx : CanvasRenderingContext2D) {
        ctx.beginPath();
        ctx.strokeStyle = "black";
        ctx.lineWidth = graphWidth / 400;
        ctx.moveTo(xMin, yOrigin);
        ctx.lineTo(xMax, yOrigin);
        ctx.stroke();
    }

    function graphTitle(ctx : CanvasRenderingContext2D, title: string) {
        ctx.beginPath();
        ctx.font = "12px Arial";
        ctx.fillStyle = "rgba(0,0,0,1)";
        ctx.textBaseline = "middle";
        ctx.textAlign = "center"
        ctx.fillText(title, xOrigin, (padding / 2) );
        ctx.stroke();
    }

    function graphXTitle(ctx : CanvasRenderingContext2D, title: string) {
        ctx.beginPath();
        ctx.font = "12px Arial";
        ctx.fillStyle = "rgba(0,0,0,1)";
        ctx.textBaseline = "bottom";
        ctx.textAlign = "center"
        ctx.fillText(title, xOrigin, canvasHeight - (padding * 1 / 2) );
        ctx.stroke();
    }

    function graphYTitle(ctx : CanvasRenderingContext2D, title: string) {
        ctx.save();
        ctx.font = "12px Arial";
        ctx.fillStyle = "rgba(0,0,0,1)";
        ctx.textBaseline = "middle";
        ctx.textAlign = "center"
        //flip x and y
        ctx.translate(0, canvasHeight);
        ctx.rotate(-Math.PI/2);
        ctx.fillText(title, canvasHeight / 2, padding * 1 / 2 );
        ctx.restore();
    }

    function graphLeftArrow(ctx : CanvasRenderingContext2D, arrowLength: number, x: number, y: number) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x + arrowLength, y + arrowLength);
        ctx.moveTo(x, y);
        ctx.lineTo(x + arrowLength, y - arrowLength);
        ctx.stroke();
    }

    function graphRightArrow(ctx : CanvasRenderingContext2D, arrowLength: number, x: number, y: number) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x - arrowLength, y + arrowLength);
        ctx.moveTo(x, y);
        ctx.lineTo(x - arrowLength, y - arrowLength);
        ctx.stroke();
    }

    function graphXAxisTickMarks(ctx : CanvasRenderingContext2D, tickMarkHeight: number,
        numberOfTickMarks: number, distanceBetweenTickMarks:number) {
        ctx.beginPath();
        ctx.font = "12px Arial";
        ctx.fillStyle = "black";
        ctx.textBaseline = "top";
        ctx.textAlign = "center"
        for (let i = 1; i < numberOfTickMarks / 2; i++) {
            ctx.moveTo(xOrigin - (i * distanceBetweenTickMarks), yOrigin - tickMarkHeight);
            ctx.lineTo(xOrigin - (i * distanceBetweenTickMarks), yOrigin + tickMarkHeight);
            ctx.fillText(`-${i * xValuePerTick}`, xOrigin - (i * distanceBetweenTickMarks), yOrigin + tickMarkHeight);
        }
        for (let i = 0; i < numberOfTickMarks / 2; i++) {
            ctx.moveTo(xOrigin + (i * distanceBetweenTickMarks), yOrigin - tickMarkHeight);
            ctx.lineTo(xOrigin + (i * distanceBetweenTickMarks), yOrigin + tickMarkHeight);
            ctx.fillText(`${i * xValuePerTick}`, xOrigin + (i * distanceBetweenTickMarks), yOrigin + tickMarkHeight);
        }
        ctx.stroke();
    }

    function graphGraph(ctx : CanvasRenderingContext2D, arrowLength: number,
        tickMarkHeight: number, numberOfTickMarks: number,
        distanceBetweenXTickMarks: number, 
        distanceBetweenYTickMarks: number, r: number, startAngle: number,
        endAngle: number, counterClockwise: boolean,
        title: string, xTitle: string, yTitle:string) {
        //graph background
        graphBackground(ctx);
        //background
        graphOuterBackground(ctx);
        //x-axis
        graphXAxis(ctx, arrowLength, tickMarkHeight,
             numberOfTickMarks, distanceBetweenXTickMarks);
        //Title
        graphTitle(ctx, title);
        //x-axis title
        graphXTitle(ctx, xTitle);
        //y-axis title
        graphYTitle(ctx, yTitle);
        //x-axis grid
        xAxisGrid(ctx, tickMarkHeight);
        //y-axis
        graphYAxis(ctx, arrowLength,
             numberOfTickMarks, tickMarkHeight, distanceBetweenYTickMarks);
        //y-axis grid    
        graphYAxisGrid(ctx, numberOfTickMarks, tickMarkHeight, distanceBetweenYTickMarks);
        //legend
        graphLegend(ctx, r, startAngle, endAngle, counterClockwise);

    }

    function xAxisGrid(ctx : CanvasRenderingContext2D, tickMarkHeight: number) {
        ctx.beginPath();
        ctx.strokeStyle = gridColor;
        let numberOfXGridMarks = 20
        let xGridMarkHeight = graphWidth;
        let distanceBetweenXGridMarks = graphWidth / numberOfXGridMarks;
        for (let i = 1; i < numberOfXGridMarks / 2; i++) {
            ctx.moveTo(xOrigin - (i * distanceBetweenXGridMarks), yOrigin - xGridMarkHeight);
            ctx.lineTo(xOrigin - (i * distanceBetweenXGridMarks), yOrigin - tickMarkHeight);
        }
        for (let i = 1; i < numberOfXGridMarks / 2; i++) {
            ctx.moveTo(xOrigin + (i * distanceBetweenXGridMarks), yOrigin - xGridMarkHeight);
            ctx.lineTo(xOrigin + (i * distanceBetweenXGridMarks), yOrigin - tickMarkHeight);
        }
        ctx.stroke();
    }

    function graphYAxis(ctx : CanvasRenderingContext2D,
         arrowLength: number, numberOfTickMarks: number,
          tickMarkHeight: number, distanceBetweenTickMarks: number) {
        //graph y line
        graphYAxisLine(ctx);
        //top arrow
        graphTopArrow(ctx, arrowLength, xOrigin, yMin);
        //y-axis tick marks
        graphYAxisTickMarks(ctx, numberOfTickMarks, tickMarkHeight, distanceBetweenTickMarks);
        
    }

    function graphYAxisLine(ctx : CanvasRenderingContext2D) {
        ctx.beginPath();
        ctx.strokeStyle = "black";
        ctx.moveTo(xOrigin, yMin);
        ctx.lineTo(xOrigin, yMax);
        ctx.stroke();
    }

    function graphTopArrow(ctx : CanvasRenderingContext2D, arrowLength: number, x: number, y: number) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x + arrowLength, y + arrowLength);
        ctx.moveTo(x, y);
        ctx.lineTo(x - arrowLength, y + arrowLength);
        ctx.stroke();
    }

    function graphBottomArrow(ctx : CanvasRenderingContext2D, arrowLength: number, x: number, y: number) {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x + arrowLength, y - arrowLength);
        ctx.moveTo(x, y);
        ctx.lineTo(x - arrowLength, y - arrowLength);
        ctx.stroke();
    }

    function graphYAxisTickMarks(ctx : CanvasRenderingContext2D, numberOfTickMarks: number,
         tickMarkHeight: number, distanceBetweenTickMarks: number) {
        ctx.beginPath();
        ctx.font = "12px Arial";
        ctx.fillStyle = "black";
        ctx.textBaseline = "middle";
        ctx.textAlign = "end";
        for (let i = 1; i < numberOfTickMarks ; i++) {
            ctx.moveTo(xOrigin - tickMarkHeight, yOrigin - (i * distanceBetweenTickMarks));
            ctx.lineTo(xOrigin + tickMarkHeight, yOrigin - (i * distanceBetweenTickMarks));
            ctx.fillText(`${i * yValuePerTick}`, padding, yOrigin - (i * distanceBetweenTickMarks));
        }
        ctx.stroke();
    }

    function graphYAxisGrid(ctx : CanvasRenderingContext2D, numberOfYGridMarks: number,
        tickMarkHeight: number, distanceBetweenYGridMarks: number) {
        //y-axis grid
        ctx.beginPath();
        ctx.strokeStyle = gridColor;
        let yGridMarkHeight = graphWidth / 2;
        for (let i = 1; i < numberOfYGridMarks; i++) {
            ctx.moveTo(xOrigin - yGridMarkHeight, yMin + (i * distanceBetweenYGridMarks));
            ctx.lineTo(xOrigin - tickMarkHeight, yMin + (i * distanceBetweenYGridMarks));
            ctx.moveTo(xOrigin + tickMarkHeight, yMin + (i * distanceBetweenYGridMarks));
            ctx.lineTo(xOrigin + yGridMarkHeight, yMin + (i * distanceBetweenYGridMarks));
        }
        ctx.stroke();
    }

    function plotPoint(ctx : CanvasRenderingContext2D, 
        x: number, y: number, distanceBetweenXGridMarks : number,
        distanceBetweenYGridMarks: number, color: string) {
        ctx.beginPath();
            

        let xOnGraph = xOrigin + (x * distanceBetweenXGridMarks / xValuePerTick);
        let yOnGraph = yOrigin - (y * distanceBetweenYGridMarks / yValuePerTick); 
        let r = 2;
        let startAngle = 0;
        let endAngle = 2 * Math.PI; 
        let counterClockwise = true;

        ctx.arc(xOnGraph, yOnGraph, r, startAngle, endAngle, counterClockwise);
        ctx.strokeStyle = color;
        ctx.fillStyle = color;
        ctx.fill();
        ctx.stroke();
    }

    function plotSpreadBetWithMatchingZone(ctx : CanvasRenderingContext2D, 
        spread: number, odds: number, distanceBetweenXGridMarks : number,
        distanceBetweenYGridMarks: number, team: string, numberOfXGridMarks: number) {
        ctx.beginPath();

        let xOnGraph = xOrigin + (spread * distanceBetweenXGridMarks / xValuePerTick);
        let yOnGraph = yOrigin - (odds * distanceBetweenYGridMarks / yValuePerTick); 
        let r = 2;
        let startAngle = 0;
        let endAngle = 2 * Math.PI; 
        let counterClockwise = true;

        ctx.arc(xOnGraph, yOnGraph, r, startAngle, endAngle, counterClockwise);

        let matchedOdds = odds / (odds - 1);
        if (team == home) {
            ctx.strokeStyle = homeColor;
            ctx.fillStyle = homeColor;
            ctx.fill();
            ctx.stroke();
            graphFilledRegion(ctx, spread, matchedOdds, numberOfXGridMarks / 2, 1, homeColorMatching, distanceBetweenXGridMarks, distanceBetweenYGridMarks);
        } else {
            ctx.strokeStyle = awayColor;
            ctx.fillStyle = awayColor;
            ctx.fill();
            ctx.stroke();
            graphFilledRegion(ctx, -1 * (numberOfXGridMarks / 2), matchedOdds, spread, 1, awayColorMatching, distanceBetweenXGridMarks, distanceBetweenYGridMarks);
        }
        graphHorizonalLine(ctx, [1,1], matchedOdds,distanceBetweenYGridMarks);
        graphVerticalLine(ctx, [1,1], spread, distanceBetweenXGridMarks);

    }

    function plotSpreadBet(ctx : CanvasRenderingContext2D, 
        spread: number, odds: number, distanceBetweenXGridMarks : number,
        distanceBetweenYGridMarks: number, team: string, numberOfXGridMarks: number) {
        ctx.beginPath();

        let xOnGraph = xOrigin + (spread * distanceBetweenXGridMarks / xValuePerTick);
        let yOnGraph = yOrigin - (odds * distanceBetweenYGridMarks / yValuePerTick); 
        let r = 2;
        let startAngle = 0;
        let endAngle = 2 * Math.PI; 
        let counterClockwise = true;

        ctx.arc(xOnGraph, yOnGraph, r, startAngle, endAngle, counterClockwise);

        let matchedOdds = odds / (odds - 1);
        if (team == home) {
            ctx.strokeStyle = homeColor;
            ctx.fillStyle = homeColor;
            ctx.fill();
            ctx.stroke();
        } else {
            ctx.strokeStyle = awayColor;
            ctx.fillStyle = awayColor;
            ctx.fill();
            ctx.stroke();
        }
    }

    function plotMatchedSpreadBet(ctx : CanvasRenderingContext2D, 
        spread1: number, odds1: number, distanceBetweenXGridMarks : number,
        distanceBetweenYGridMarks: number, team1: string, numberOfXGridMarks: number,
        spread2: number, odds2: number, team2: string) {
        plotSpreadBet(ctx, spread1, odds1, distanceBetweenXGridMarks, distanceBetweenYGridMarks, team1, numberOfXGridMarks);
        plotSpreadBet(ctx, spread2, odds2, distanceBetweenXGridMarks, distanceBetweenYGridMarks, team2, numberOfXGridMarks);
        graphLineSegment(ctx, [1,1], spread1, odds1, spread2, odds2, distanceBetweenXGridMarks, distanceBetweenYGridMarks);
        
    }

    function graphLegendBox(ctx : CanvasRenderingContext2D, xLegend: number, yLegend: number) {
        ctx.beginPath();
        ctx.setLineDash([]);
        ctx.strokeStyle = "rgba(0,0,0,1)";
        ctx.fillStyle = "rgba(255,255,255,1)";
        ctx.strokeRect(xLegend, yLegend, graphWidth / 2, padding * 1 / 2);
        ctx.stroke();
    }

    function graphLegendTitle(ctx : CanvasRenderingContext2D, xLegend: number, yLegend: number) {
        ctx.beginPath();
        ctx.font = "12px Arial";
        ctx.fillStyle = "black";
        ctx.textBaseline = "top";
        ctx.textAlign = "start";
        ctx.fillText(`Legend`, xLegend + (padding * 1/6), yLegend );
        ctx.stroke();
    }

    function graphLegend(ctx : CanvasRenderingContext2D, r: number, startAngle: number,
        endAngle: number, counterClockwise: boolean ) {
        let xLegend = 0;
        let yLegend = yOrigin + (padding * 1 / 2);
        graphLegendBox(ctx, xLegend, yLegend);
        graphLegendTitle(ctx, xLegend, yLegend)
        graphHomeLegendTitle(ctx, xLegend, yLegend, r, startAngle, endAngle, counterClockwise);
        graphAwayLegendTitle(ctx, xLegend, yLegend, r, startAngle, endAngle, counterClockwise);
    }

    function graphHomeLegendTitle(ctx : CanvasRenderingContext2D,
         xLegend: number, yLegend: number, r: number, startAngle: number,
         endAngle: number, counterClockwise: boolean) {
        ctx.beginPath();
        ctx.fillStyle = homeColor;
        ctx.fillText(`Home Bet`, xLegend + (padding * 1/6), yLegend + (padding * 1/6));
        ctx.arc(xLegend + (padding * 1/12), yLegend + (padding * 1/6) + 6, r, startAngle, endAngle, counterClockwise);
        ctx.strokeStyle = homeColor;
        ctx.fillStyle = homeColor;
        ctx.fill();
        ctx.stroke();
    }

    function graphAwayLegendTitle(ctx : CanvasRenderingContext2D,
        xLegend: number, yLegend: number, r: number, startAngle: number,
        endAngle: number, counterClockwise: boolean) {
        ctx.beginPath();
        ctx.fillStyle = awayColor;
        ctx.fillText(`Away Bet`, xLegend + (padding * 1/6), yLegend + (padding * 1/3));
        ctx.arc(xLegend + (padding * 1/12), yLegend + (padding * 1/3) + 6, r, startAngle, endAngle, counterClockwise);
        ctx.strokeStyle = awayColor;
        ctx.fillStyle = awayColor;
        ctx.fill();
        ctx.stroke();
   }

   function graphHorizonalLine(ctx : CanvasRenderingContext2D, pattern : number[], y: number, distanceBetweenYGridMarks: number) {
    ctx.beginPath();
    ctx.strokeStyle = "rgba(0,0,255,1)";
    ctx.fillStyle = "rgba(0,0,255,1)";
    let yOnGraph = yOrigin - (y * distanceBetweenYGridMarks / yValuePerTick); 
    ctx.setLineDash(pattern);
    ctx.moveTo(padding, yOnGraph);
    ctx.lineTo(padding + graphWidth, yOnGraph);
    ctx.stroke();
    graphLeftArrow(ctx, arrowLength, padding, yOnGraph);
    graphRightArrow(ctx, arrowLength, padding + graphWidth, yOnGraph);
   }

   function graphVerticalLine(ctx : CanvasRenderingContext2D, pattern : number[], x: number, distanceBetweenXGridMarks: number) {
    graphLineSegment(ctx, pattern, x, 0, x, 5,
         distanceBetweenXGridMarks, distanceBetweenYTickMarks);
    let xOnGraph = xOrigin + (x * distanceBetweenXGridMarks / xValuePerTick);
    graphTopArrow(ctx, arrowLength, xOnGraph, padding);
    graphBottomArrow(ctx, arrowLength, xOnGraph, padding + graphHeight);
   }

   function graphLineSegment(ctx : CanvasRenderingContext2D, pattern : number[],
     x1: number, y1: number, x2: number, y2:number, distanceBetweenXGridMarks: number, distanceBetweenYGridMarks: number) {
        ctx.beginPath();
        ctx.strokeStyle = "rgba(0,0,255,1)";
        ctx.fillStyle = "rgba(0,0,255,1)";
        ctx.setLineDash(pattern);
        let x1OnGraph = xOrigin + (x1 * distanceBetweenXGridMarks / xValuePerTick);
        let x2OnGraph = xOrigin + (x2 * distanceBetweenXGridMarks / xValuePerTick);
        let y1OnGraph = yOrigin - (y1 * distanceBetweenYGridMarks / yValuePerTick); 
        let y2OnGraph = yOrigin - (y2 * distanceBetweenYGridMarks / yValuePerTick); 
        ctx.moveTo(x1OnGraph, y1OnGraph);
        ctx.lineTo(x2OnGraph, y2OnGraph);
        ctx.stroke();
    }

    function graphFilledRegion(ctx : CanvasRenderingContext2D,
        topLeftX: number, topLeftY: number, bottomRightX: number, bottomRightY:number, color: string,
        distanceBetweenXGridMarks: number, distanceBetweenYGridMarks: number) {
           ctx.beginPath();
           ctx.strokeStyle = "rgba(0,0,255,1)";
           ctx.fillStyle = color; //"rgba(0,0,255,1)";
           let topLeftXOnGraph = xOrigin + (topLeftX * distanceBetweenXGridMarks / xValuePerTick);
           let bottomRightXOnGraph = xOrigin + (bottomRightX * distanceBetweenXGridMarks / xValuePerTick);
           let topLeftYOnGraph = yOrigin - (topLeftY * distanceBetweenYGridMarks / yValuePerTick); 
           let bottomRightYOnGraph = yOrigin - (bottomRightY * distanceBetweenYGridMarks / yValuePerTick); 
           ctx.fillRect(topLeftXOnGraph, topLeftYOnGraph, bottomRightXOnGraph - topLeftXOnGraph, bottomRightYOnGraph - topLeftYOnGraph);
           //ctx.stroke();
           
       }

    
    

    useEffect(() => {
        let ctx = getContext();
        if (ctx) {
            //graph graph
            graphGraph(ctx, arrowLength, tickMarkHeight,
                numberOfTickMarks, distanceBetweenXTickMarks,
                distanceBetweenYTickMarks, r,
                 startAngle, endAngle, counterClockwise,
                 `Decimal Odds vs Home Team Spread (Matched)`,
                 `Home Team Spread (points)`,
                 `Decimal Odds`);
            /*
            //plot home point
            let x = 5.5;
            let y = 2.0;
            let colorGreen = "rgba(0,255, 0, 1)";
            plotPoint(ctx, x , y,
                 distanceBetweenXTickMarks,
                  distanceBetweenYTickMarks, colorGreen)
            
            //plot home point
            let x = 0.5;
            let y = 3.0;
            let colorGreen = "rgba(0,255, 0, 1)";
            plotPoint(ctx, x , y,
                    distanceBetweenXTickMarks,
                    distanceBetweenYTickMarks, colorGreen)
            
            //plot home point
            let x = 0.5;
            let y = 1.5;
            let colorGreen = "rgba(255,0, 0, 1)";
            plotPoint(ctx, x , y,
                    distanceBetweenXTickMarks,
                    distanceBetweenYTickMarks, colorGreen)
            
            //graph vertical line
            graphVerticalLine(ctx, [5,5], 2, distanceBetweenXTickMarks);

            graphFilledRegion(ctx, 0, 0, 5, 5, "rgba(237, 233, 157, 0.5)", distanceBetweenXTickMarks, distanceBetweenYTickMarks);
            */
            //home team win
            //plotSpreadBetWithMatchingZone(ctx, -0.5, 1.5, distanceBetweenXTickMarks, distanceBetweenYTickMarks, home, numberOfTickMarks);
            //away team win
            //plotSpreadBet(ctx, 0.5, 3, distanceBetweenXTickMarks, distanceBetweenYTickMarks, away, numberOfTickMarks);
            //plotSpreadBet(ctx, 1.5, 2, distanceBetweenXTickMarks, distanceBetweenYTickMarks, away, numberOfTickMarks);
            //plotSpreadBet(ctx, -0.5, 2.02, distanceBetweenXTickMarks, distanceBetweenYTickMarks, away, numberOfTickMarks);
            plotMatchedSpreadBet(ctx, 0.5, 1.5, distanceBetweenXTickMarks, distanceBetweenYTickMarks, home, numberOfTickMarks, 0.5, 3, away);
            plotMatchedSpreadBet(ctx, -0.5, 2.0, distanceBetweenXTickMarks, distanceBetweenYTickMarks, home, numberOfTickMarks, -0.5, 2.0, away);
            plotMatchedSpreadBet(ctx, 1.5, 1.25, distanceBetweenXTickMarks, distanceBetweenYTickMarks, home, numberOfTickMarks, 1.5, 5.0, away);
        }
    });
    return (
        <canvas id="myCanvas" width={canvasWidth} height={canvasHeight}></canvas>
    )
};
