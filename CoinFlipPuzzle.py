#! python

#This program is made to solve the puzzle on vox.com 10/16/16 concerning coins.

#Function start_coins just sets up a list of representing 100 coins facing up.
#In this program, "coins[6] = 1" means than coin 7 is facing heads up.
def start_coins():
    blank_coins = [1 for i in range(100)]
    return blank_coins

#A function so I can see the state of the coins formatted.
def print_coins(coinslist):
    for i in range(10):
        TenCoinString = ""
        for j in range(10):
            TenCoinString += str( coinslist[ (10*i) + j ] )
            TenCoinString += " "
            #print (i, j)
        print(TenCoinString + '\n')
    return(0)

#This function flips every n coins in the series.
def flip(SomeList, n):
    for i in range(100):
        j = i + 1                   #j is an index that starts at 1, not zero
        if( j % n == 0 and SomeList[i] == 1):
            SomeList[i] = 0
        elif( j % n == 0 and SomeList[i] == 0):
            SomeList[i] = 1
    return SomeList


coins = start_coins()
for i in range(100):
    coins = flip(coins, i+1)
    print_coins(coins)
    print( '\n' + "Coins flipped " + str(i) + " times" + '\n')

