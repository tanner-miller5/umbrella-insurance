import '../../css/bets/placeBet.css';
import { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import Chart from '../charts/Chart';
import React from 'react';
import Header from '../headers/Header';
import { updateCurrentPage } from '../../redux/reducers/AppReducer';
import { useDispatch } from 'react-redux';

export default function PlaceBet(){
    const [placeBetGameId, updatePlaceBetGameId] = useState(null);
    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/placeBet"));
        }, []
    )
    return (
        <div className='column2'>
            <div className="border">
                <h2>Game Information</h2>
                <table id="foobar">
                    <tbody>
                        <tr>
                            <th>Date</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Home Team</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Away Team</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Home Score</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Away Score</th>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div className="border">
                <h2 >Place Bet</h2>
                <div className="border" >
                    <h3></h3>
                    <div className="form">
                        <table>
                            <tbody>
                                <tr>
                                    <td></td>
                                    <td ></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td>Win</td>
                                    <td >
                                        <div>
                                        <input name="bet" type="radio"
                                                />
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                        <input name="bet" type="radio"
                                                />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Spread</td>
                                    <td >
                                        <div>
                                            <input name="bet" type="radio"
                                                    />
                                            <input max="100.5" min="-100.5" step="0.5"
                                                    type="number" />
                                        </div>
                                    </td>
                                    <td >
                                        <div>
                                            <input name="bet" type="radio"
                                                    />
                                            <input className="noLeftBorder" 
                                                    type="text"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Over/Under</td>
                                    <td >
                                        <div>
                                            <input name="bet" type="radio"
                                                    />
                                            <input max="100" min="0.5" step="0.5"
                                                    type="number"
                                                    />
                                        </div>
                                    </td>
                                    <td >
                                        <div>
                                            <input name="bet"  type="radio"
                                                    />
                                            <input  type="text"
                                                    />
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="border">
                    <select id="additionalGame">
                        <option >
                        </option>
                    </select>
                    <Button>Add Game For Parlay Bet</Button>
                </div>
                <div className="border hide" id="deleteAdditionalGameDiv">
                    <select id="deleteAdditionalGame">
                    </select>
                    <Button className="deleteButton">Remove Parlay Game</Button>
                </div>
                <div className="border">
                    <div>
                        <label>Select what group that can fill your bet order:</label>
                        <select id="groupBet" name="groupBet">
                            <option >Everyone</option>
                            <option >coolKidsGroup</option>
                            <option >Create new group</option>
                        </select>
                    </div>
                    <div>
                        <label>Cancel bet if not filled by:</label>
                        <br/>
                        <select id="cancelBetTimeSelect" name="cancelBetTime">
                            <option >Start of game</option>
                            <option >End of 1st Quarter</option>
                            <option >End of Halftime</option>
                            <option >End of 3rd Quarter</option>
                            <option >Other</option>
                        </select>
                        <br/>
                        <input id="cancelBetTimeInput" max="2018-06-14T00:00" min="2018-06-07T00:00"
                            name="cancelTime"
                            type="datetime-local"/>
                    </div>
                    <div className="form">
                        <br/>
                        <label >Select Currency</label>
                        <br/>
                        <select id="currency" name="currency" required>
                            <option >Butter Bucks</option>
                            <option >USD</option>
                        </select>
                    </div>
                    <div className="form">
                        <label>Wager Amount</label>
                        <input id="amount" min="0" name="amount" required step="0.01" type="number"/>
                    </div>
                    <div className="form" id="odds_form">
                        <label >Minimum odds</label>
                        <input id="minimumOdds" max="100" min="1.01" name="minimumOdds" required step="0.10"
                            type="number"
                            />
                    </div>
                </div>
                <div className="border">
                    <div>
                        <label id="wagerAmountDisplay">Wager Amount: x</label>
                        <br/>
                        <label id="decimalOddsDisplay">Decimal Odds: x</label>
                        <br/>
                        <label id="payoffBeforeFeeDisplay">Payoff Bet Amount Before Fee: x</label>
                        <br/>
                        <label id="feeAmountDisplay">Umbrella Insurance Fee Amount: x</label>
                        <br/>
                        <label id="payoffDisplay">Payoff Bet Amount: x</label>
                        <br/>
                    </div>
                    <div>
                        <br/>
                        <label id="acceptorMaxAmountDisplay">Acceptor Max Amount: x</label>
                        <br/>
                        <label id="acceptorDecimalOddsDisplay">Acceptor Decimal Odds: x</label>
                        <br/>
                        <label id="acceptorMaxPayoffBeforeFeeDisplay">Acceptor Max Payoff Before Fee: x</label>
                        <br/>
                        <label id="acceptorFeeAmountDisplay">Umbrella Insurance Fee Amount: x</label>
                        <br/>
                        <label id="acceptorPayoffDisplay">Payoff Bet Amount: x</label>
                        <br/>
                    </div>
                    <div>
                        <label id="spreadTeamDisplay">Spread Team: x</label>
                        <br/>
                        <label id="spreadOutcomeDisplay">Spread Outcome: x</label>
                        <br/>
                        <label id="spreadAmountDisplay">Spread Amount: x</label>
                        <br/>
                        <label id="acceptorSpreadTeamDisplayHeader">Spread Team</label>
                        <br/>
                        <label id="acceptorSpreadAmountDisplayHeader">Spread Amount</label>
                        <br/>
                    </div>
                    <div>
                        <label id="overUnderOutcomeDisplay">Over/Under Outcome: x</label>
                        <br/>
                        <label id="overUnderAmountDisplay">Over/Under Amount: x</label>
                        <br/>
                        <label id="acceptorOverUnderOutcomeDisplayHeader">Over/Under Outcome</label>
                        <br/>
                        <label id="acceptorOverUnderAmountDisplayHeader">Over/Under Amount</label>
                        <br/>
                    </div>
                    <div>
                        <label id="straightUpOutcomeDisplay">Straight Up Outcome: x</label>
                        <br/>
                        <label id="straightUpTeamDisplay">Straight Up Team: x</label>
                        <br/>
                        <label id="acceptorStraightUpOutcomeDisplayHeader">Straight Up Outcome</label>
                        <br/>
                        <label id="acceptorStraightUpTeamDisplayHeader">Straight Up Team</label>
                        <br/>
                    </div>
                    <br/>
                    <div>
                        <input name="action" type="submit"/>
                    </div>
                </div>
            </div>
            <Chart></Chart>
        </div>
    );
};