import '../../css/bets/myBets.css';
import React from 'react';
import { useState } from 'react';

export default function MyBets(){

    return (
        <div className='column2'>
        <h2>My Bets</h2>
        <table>
            <caption>Over Under</caption>
            <thead>
            <tr>
            <th>Max Wager Amount</th>
            <th>Odds</th>
            <th>Bet Type</th>
            <th>Game Outcome(s)</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <table>
            <caption>Straight Up</caption>
            <thead>
            <tr>
            <th>Max Wager Amount</th>
            <th>Odds</th>
            <th>Bet Type</th>
            <th>Team(s)</th>
            <th>Game Outcome(s)</th>
            </tr>
            </thead>
            <tbody >
            </tbody>
        </table>
        <table>
            <caption>Spread</caption>
            <thead>
            <tr>
            <th>Max Wager Amount</th>
            <th>Odds</th>
            <th>Bet Type</th>
            <th>Team(s)</th>
            <th>Game Outcome(s)</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>);
};