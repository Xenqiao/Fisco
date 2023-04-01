// SPDX-License-Identifier: MIT
pragma solidity ^0.6.10;

contract HelloWorld {

    event SetWord(string word, address operator);

    string private word;

    function sayWord() external view returns(string memory) {
        return word;
    }

    function setWord(string memory _word) external {
        word = _word;

        emit SetWord(_word, msg.sender);
    }
}