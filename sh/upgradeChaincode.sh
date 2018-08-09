red=$'\e[1;31m'
grn=$'\e[1;32m'
end=$'\e[0m'
if docker exec -e "CORE_PEER_LOCALMSPID=Org1MSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp" cli peer chaincode install -n minerva-app -v $1 -p github.com/minerva-app
   docker exec -e "CORE_PEER_LOCALMSPID=Org1MSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp" cli peer chaincode upgrade -n minerva-app -v $1 -p github.com/minerva-app -C mychannel -c '{"Args":[""]}' ; then
    printf "\n${grn}Chaincode upgrade succeeded.${end} \n\n"
else
    printf "\n${red}Chaincode upgrade failed.${end} \n\n"
fi
