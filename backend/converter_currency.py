from currency_converter import CurrencyConverter
import sys


if len(sys.argv) != 4:
    print("Usage: python script.py <amount> <from_currency> <to_currency>")
    sys.exit(1)

c = CurrencyConverter()


# Parse command-line arguments
amount = float(sys.argv[1])
from_currency = sys.argv[2]
to_currency = sys.argv[3]


print(c.convert(amount, from_currency, to_currency))
