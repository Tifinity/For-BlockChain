pragma solidity >=0.4.22 <0.7.0;
contract test2{
	struct Receipt {
		address come;
		address to;
		uint amount;
		string status;
	}

	struct Company {
		string name;
		string addr;
		int t;
	}
    
    mapping (address => Company) public companys;
    mapping (address => uint) public balances;
    
    Receipt[] public receipts;
    address public bank;
	
	constructor() {
	   bank = msg.sender; 
	}
    
    function init(uint money) {
        balances[msg.sender] = money;
    }
    
	function receivable_account(address _receive, uint _amount, string _status) {
		receipts.push(Receipt({
		    come: msg.sender, 
		    to: _receive, 
		    amount: _amount, 
		    status: _status
		}));
	}
    
	function transfer(address _receive, uint _amount, string _status) {
	    for(uint i=0; i<receipts.length; i++) {
	        if(receipts[i].to == msg.sender) {
	            receipts[i].amount -= _amount;
	            receipts.push(Receipt({
        		    come: receipts[i].come, 
        		    to: _receive, 
        		    amount: _amount, 
        		    status: _status
        		}));
        		break;
	        }
	    }
 	}
    
	function financing(uint money) {
		uint sum = 0;
		for(uint i=0; i<receipts.length; i++) {
	        if(receipts[i].to == msg.sender) {
	            sum += receipts[i].amount;
	        }
	    }
	    if (sum < money) return;
        receipts.push(Receipt({
    	    come: msg.sender, 
    	    to: bank, 
    	    amount: money, 
    	    status: '1'
    	}));
	    balances[msg.sender] += money;
	    balances[bank] -= money;
	}
    
	function account_settlement() {
		for(uint i=0; i<receipts.length; i++) {
	        if(receipts[i].come == msg.sender) {
	            balances[msg.sender] -= receipts[i].amount;
	            balances[receipts[i].to] += receipts[i].amount;
	            remove(i);
	            i -= 1;
	        }
	    }
	}
	
	function remove(uint index) {
	    if (index>=receipts.length) return;
	    for(uint i=index; i<receipts.length-1; i++) {
	        receipts[i] = receipts[i+1];
	    }
	    delete receipts[receipts.length-1];
	    receipts.length--;
	}
}
