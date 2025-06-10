import { Charity } from "../../../../charities/v1/Charity";
import { Game } from "../../../../games/v1/Game";
import { Unit } from "../../../../units/v1/Unit";
import { Bot } from "../../../bots/v1/Bot";
import { PendingBetState } from "../pendingBetStates/v1/PendingBetState";
import { Session } from "../../../sessions/v1/Session";
import { Fee } from "../../../fees/v1/Fee";
import { AccountBalanceTransaction } from "../../../accountBalances/accountBalanceTransactions/v1/AccountBalanceTransaction";
import { BetType } from "../../betTypes/v1/BetType";
import { OrderType } from "../../../../orderTypes/v1/OrderType";

export class PendingBet {
    id?: number;
    session?: Session;
    createdDateTime?: string;
    game?: Game;
    minimumOdds?: number;
    maximumWagerAmount?: number;
    unit?: Unit;//currency
    bot?: Bot;
    isBotBet?: boolean;
    isCharityBet?: boolean;
    charity?: Charity;
    pendingBetState?: PendingBetState;
    fee?: Fee;
    pendingBetName?: string;
    splitPendingBet1?: PendingBet;
    splitPendingBet2?: PendingBet;
    originalPendingBet?: PendingBet;
    accountBalanceEscrowTransaction?: AccountBalanceTransaction;
    accountBalanceCanceledEscrowTransaction?: AccountBalanceTransaction;
    canceledDateTime?: string;
    betType?: BetType;
    orderType?: OrderType;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.pendingBetName) {
                this.pendingBetName = obj.pendingBetName;
            }
            if(obj?.session) {
                this.session = new Session(obj.session);
            }
            if(obj?.createdDateTime) {
                this.createdDateTime = obj.createdDateTime;
            }
            if(obj?.game) {
                this.game = new Game(obj.game);
            }
            if(obj?.minimumOdds) {
                this.minimumOdds = obj.minimumOdds;
            }
            if(obj?.maximumWagerAmount) {
                this.maximumWagerAmount = obj.maximumWagerAmount;
            }
            if(obj?.unit) {
                this.unit = new Unit(obj.unit);
            }
            if(obj?.bot) {
                this.bot = new Bot(obj.bot);
            }
            if(typeof obj?.isBotBet === "boolean") {
                this.isBotBet = obj.isBotBet;
            }
            if(typeof obj?.isCharityBet === "boolean") {
                this.isCharityBet = obj.isCharityBet;
            }
            if(obj?.charity) {
                this.charity = new Charity(obj.charity);
            }
            if(obj?.pendingBetState) {
                this.pendingBetState = new PendingBetState(obj.pendingBetState);
            }
            if(obj?.fee) {
                this.fee = new Fee(obj.fee);
            }
            if(obj?.pendingBetName) {
                this.pendingBetName = obj.pendingBetName;
            }
            if(obj?.splitPendingBet1) {
                this.splitPendingBet1 = new PendingBet(obj.splitPendingBet1);
            }
            if(obj?.splitPendingBet2) {
                this.splitPendingBet2 = new PendingBet(obj.splitPendingBet2);
            }
            if(obj?.originalPendingBet) {
                this.originalPendingBet = new PendingBet(obj.originalPendingBet);
            }
            if(obj?.accountBalanceEscrowTransaction) {
                this.accountBalanceEscrowTransaction = new AccountBalanceTransaction(obj.accountBalanceEscrowTransaction);
            }
            if(obj?.accountBalanceCanceledEscrowTransaction) {
                this.accountBalanceCanceledEscrowTransaction = new AccountBalanceTransaction(obj.accountBalanceCanceledEscrowTransaction);
            }
            if(obj?.canceledDateTime) {
                this.canceledDateTime = obj.canceledDateTime;
            }
            if(obj?.betType) {
                this.betType = new BetType(obj.betType);
            }
            if(obj?.orderType) {
                this.orderType = new OrderType(obj.orderType);
            }
        }
    }
}