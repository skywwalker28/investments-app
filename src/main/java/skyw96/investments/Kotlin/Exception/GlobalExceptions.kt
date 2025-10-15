package skyw96.investments.Kotlin.Exception

class InsufficientFundsException(message: String) : RuntimeException(message)
class InsufficientStocksException(message: String): RuntimeException(message)
class AccountNotFoundException(message: String): RuntimeException(message)
class StockNotFoundException(message: String): RuntimeException(message)
class StockAlreadyExistException(message: String): RuntimeException(message)
class EmailAlreadyExistException(message: String): RuntimeException(message)
class EmailNotFoundException(message: String): RuntimeException(message)
class AccessDeniedException(message: String): RuntimeException(message)